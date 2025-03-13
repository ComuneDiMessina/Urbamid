using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.Geodatabase;
using Newtonsoft.Json;
using NLog;
using RestSharp;
using RestSharp.Authenticators;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Web.Script.Serialization;
using System.Windows.Forms;
using System.Xml.Linq;
using UrbamidAddIn_V10_2.Dto;

namespace UrbamidAddIn_V10_2.Utility
{
    public class RestApiGeoServer : Utility
    {
        public LayerGroupDto getLayergroups(GetGroupLayerDto dto, NLog.Logger logger)
        {
            try
            {
                LayerGroupDto output = null;
                String restUrl = dto.baseUrl;
                restUrl += Costanti.REST_SERVICE_PREFIX + dto.workspace + "/layergroups.json";
                var client = new RestSharp.RestClient(restUrl);
                client.Authenticator = new HttpBasicAuthenticator(dto.username, dto.password);
                var request = new RestSharp.RestRequest(Method.GET);
                RestSharp.IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {
                    // LayerDTO json = new JavaScriptSerializer().Deserialize<LayerDTO>(response.Content);
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");

                    output = JsonConvert.DeserializeObject<LayerGroupDto>(response.Content, settings);
                    return output;
                }
                else
                {
                    logger.Error($"Errore Get Layer Group {restUrl} Status Code: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.ListaGruppi);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Error Layer Groups ");
                throw new UrbamidException(TipoErroreEnum.ListaGruppi);

            }
        }

        public void UploadRaster(UploadRasterDto input, NLog.Logger logger)
        {
            String server = String.Empty;
            try
            {
                String extension = String.IsNullOrEmpty(input.extension) ? ".geotiff" : input.extension;
                server = String.Concat(input.baseUrl, Costanti.REST_SERVICE_PREFIX,
                    input.workspace, "/coveragestores/", Path.GetFileNameWithoutExtension(input.nome), "/file", extension, "?filename=" + input.nome);
                WebRequest request = WebRequest.Create(server);
                request.ContentType = "application/json";
                request.Method = "PUT";
                request.Credentials = new NetworkCredential(input.username, input.password);
                Stream requestStream = request.GetRequestStream();
                requestStream.Write(input.arrFile, 0, input.arrFile.Length);
                requestStream.Close();

                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                if (response.StatusCode == HttpStatusCode.Created ||
                    response.StatusCode == HttpStatusCode.OK ||
                    response.StatusCode == HttpStatusCode.Accepted)
                    response.Close();
                else
                {
                    logger.Error($"Raster non importato {server} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.UploadRaster);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore Upload Raster");
                throw new UrbamidException(TipoErroreEnum.UploadRaster);
            }


        }

        /// <summary>
        /// Upload un file con estensione .zip 
        /// Crea DataStore e Layer
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public void UploadShapeFile(GeoServerDto input, NLog.Logger logger)
        {
            String server = String.Empty;
            try
            {
                server = String.Concat(input.baseUrl, Costanti.REST_SERVICE_PREFIX,
                    input.workspace, "/datastores/", input.storeName, "/file.shp?configure=all&update=overwrite&filename=", input.layerName);

                byte[] localShapeFile = readLocalShapeFile(input.pathFile, logger);
                WebRequest request = WebRequest.Create(server);
                request.ContentType = "application/zip";
                request.Method = "PUT";
                request.Credentials = new NetworkCredential(input.username, input.password);
                Stream requestStream = request.GetRequestStream();
                requestStream.Write(localShapeFile, 0, localShapeFile.Length);
                requestStream.Close();

                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                if (response.StatusCode == HttpStatusCode.Created ||
                    response.StatusCode == HttpStatusCode.OK ||
                    response.StatusCode == HttpStatusCode.Accepted)
                {
                    response.Close();
                }
                else
                {
                    logger.Error($"Errore UPLOAD SHAPE FILE {server}, STATUS CODE: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.UploadShape);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "UploadShapeFile");
                throw new UrbamidException(TipoErroreEnum.UploadShape); ;
            }


        }

        ///// <summary>
        ///// Riproietta il file con EPSG:4326
        ///// </summary>
        ///// <param name="input"></param>
        ///// <returns></returns>
        //public Boolean EditSrs(GeoServerDto input)
        //{
        //    String urlRequest = String.Empty;


        //    String body = "<featureType><srs>EPSG:4326</srs><projectionPolicy>REPROJECT_TO_DECLARED</projectionPolicy></featureType>";
        //    byte[] bytes = Encoding.UTF8.GetBytes(body);
        //    try
        //    {
        //        if(String.IsNullOrEmpty(input.linkDettaglio))
        //        urlRequest = String.Concat(input.baseUrl, Costanti.REST_SERVICE_PREFIX, input.workspace,
        //            "/datastores/", input.layerName, "/featuretypes/", input.storeName, "?recalculate=nativebbox,latlonbbox");
        //        else
        //            urlRequest = String.Concat(input.linkDettaglio, "?recalculate=nativebbox,latlonbbox"); 

        //        WebRequest request = WebRequest.Create(urlRequest);
        //        request.ContentType = "application/xml";
        //        request.Method = "PUT";
        //        request.Credentials = new NetworkCredential(input.username, input.password);
        //        request.GetRequestStream().Write(bytes, 0, bytes.Length);



        //        HttpWebResponse response = (HttpWebResponse)request.GetResponse();
        //        if (response.StatusCode == HttpStatusCode.Created ||
        //            response.StatusCode == HttpStatusCode.OK ||
        //            response.StatusCode == HttpStatusCode.Accepted)
        //        {
        //            response.Close();
        //            return true;
        //        }
        //        else
        //        {
        //            message(TipoMessaggio.ERRORE, "Errore edit riproiezione layer");
        //            scriviLog(response.StatusDescription);
        //            response.Close();
        //            return false;
        //        }
        //    }
        //    catch (Exception ex)
        //    {
        //        message(TipoMessaggio.ERRORE, "Errore modifica riproiezione Layer");
        //        scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
        //        return false;

        //    }
        //}


        //public Boolean EditSrsRaster(CoverageDto input, BaseDto dtoBase)
        //{

        //    input.coverage.latLonBoundingBox.crs = "EPSG:4326";
        //    input.coverage.projectionPolicy = "REPROJECT_TO_DECLARED";
        //    input.coverage.enabled = true;
        //   // input.coverage.metadata = input.coverage.metadata;
        //    String urlRequest = String.Empty;
        //    string json = new JavaScriptSerializer().Serialize(new { input.coverage });
        //    json = json.Replace("tipoPublished", "@type");
        //    //json = json.Replace("null", "\"null\"");
        //    json = json.Replace("###", "null");
        //    json = json.Replace("chiave", "string");
        //    json = json.Replace("NameSpaceValue", "namespace");
        //    json = json.Replace("Classe", "@class");
        //    json = json.Replace("Valore", "$");
        //    json = json.Replace("RequestSrs", "string");
        //    json = json.Replace("ResponseSrs", "string");
        //    json = json.Replace("SupportedFormatsValues", "string");
        //    json = json.Replace("Valori", "$");
        //    json = json.Replace("InterpolationMethodsValues", "string");
        //    json = json.Replace("EntryKey", "@key");
        //    json = json.Replace("GridDimension", "@dimension");



        //    byte[] bytes = Encoding.UTF8.GetBytes(json);


        //    try
        //    {

        //        urlRequest = String.Concat(dtoBase.baseUrl, Costanti.REST_SERVICE_PREFIX, dtoBase.workspace,
        //            "/coveragestores/", Path.GetFileNameWithoutExtension(input.coverage.name), "/coverages/", Path.GetFileNameWithoutExtension(input.coverage.name), "?recalculate=nativebbox,latlonbbox");
        //        WebRequest request = WebRequest.Create(urlRequest);
        //        request.ContentType = "application/json";
        //        request.Method = "PUT";
        //        request.Credentials = new NetworkCredential(dtoBase.username, dtoBase.password);
        //        request.GetRequestStream().Write(bytes, 0, bytes.Length);



        //        HttpWebResponse response = (HttpWebResponse)request.GetResponse();
        //        if (response.StatusCode == HttpStatusCode.Created ||
        //            response.StatusCode == HttpStatusCode.OK ||
        //            response.StatusCode == HttpStatusCode.Accepted)
        //        {
        //            response.Close();
        //            return true;
        //        }
        //        else
        //        {
        //            message(TipoMessaggio.ERRORE, "Errore edit riproiezione layer");
        //            scriviLog(response.StatusDescription);
        //            response.Close();
        //            return false;
        //        }
        //    }
        //    catch (Exception ex)
        //    {
        //        message(TipoMessaggio.ERRORE, "Errore modifica riproiezione Layer");
        //        scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
        //        return false;

        //    }
        //}

        /// <summary>
        /// Agginge un layer esistente ad un layer group
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public Boolean AddLayerToGroup(LayerDTO input, NLog.Logger logger)
        {
            String urlRequest = String.Empty;

            try
            {
                string json = new JavaScriptSerializer().Serialize(new { input.layerGroup });
                json = json.Replace("tipoPublished", "@type");
                json = json.Replace("null", "\"null\"");
                json = json.Replace("###", "null");

                json = json.Replace("chiave", "string");


                byte[] bytes = Encoding.UTF8.GetBytes(json);


                urlRequest = String.Concat(input.baseUrl, Costanti.REST_SERVICE_PREFIX, input.layerGroup.workspace.name,
                    "/layergroups/", input.layerGroup.name);

                WebRequest request = WebRequest.Create(urlRequest);
                request.ContentType = "application/json";
                request.Method = "PUT";
                request.Credentials = new NetworkCredential(input.username, input.password);
                request.GetRequestStream().Write(bytes, 0, bytes.Length);



                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                if (response.StatusCode == HttpStatusCode.Created ||
                    response.StatusCode == HttpStatusCode.OK ||
                    response.StatusCode == HttpStatusCode.Accepted)
                {
                    response.Close();
                    return true;
                }
                else
                {
                    logger.Error($"Errore AddLayerToGroup {urlRequest} STATUS CODE: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.AddLayerToGroup);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore AddLayerToGroup");
                throw new UrbamidException(TipoErroreEnum.AddLayerToGroup);

            }
        }


        public LayerDTO getLayergroupsByName(GetGroupLayerDto dto, NLog.Logger logger)
        {
            LayerDTO output = null;
            try
            {
                String restUrl = dto.baseUrl;
                if (String.IsNullOrEmpty(dto.link))
                    restUrl += Costanti.REST_SERVICE_PREFIX + dto.workspace + "/layergroups/" + dto.nomeNodo + ".json";
                else
                    restUrl = dto.link;

                var client = new RestClient(restUrl)
                {
                    Authenticator = new HttpBasicAuthenticator(dto.username, dto.password)
                };

                var request = new RestRequest(Method.GET);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {
                    // LayerDTO json = new JavaScriptSerializer().Deserialize<LayerDTO>(response.Content);
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Include,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("null", "");
                    response.Content = response.Content.Replace("Null", "");
                    response.Content = response.Content.Replace("NULL", "");

                    output = JsonConvert.DeserializeObject<LayerDTO>(response.Content, settings);
                    return output;
                }
                else
                {
                    logger.Error(" Errore getLayergroupsByName Status Code: " + response.StatusCode + " Chiamata: " + restUrl);
                    throw new UrbamidException(TipoErroreEnum.DettaglioGruppo);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, " Errore getLayergroupsByName " + ex.Message + " Stack: " + ex.StackTrace);
                throw new UrbamidException(TipoErroreEnum.DettaglioGruppo);

            }
        }

        public LayerDetailDto getDettaglioLayer(GeoServerDto input, NLog.Logger logger)
        {
            String urlRequest = String.Empty;
            try
            {
                LayerDetailDto output = null;
                if (String.IsNullOrEmpty(input.linkDettaglio))
                    urlRequest = input.baseUrl + Costanti.REST_SERVICE_PREFIX + input.workspace + "/layers/" + input.storeName;
                else
                    urlRequest = input.linkDettaglio;

                var client = new RestClient(urlRequest);
                client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                var request = new RestRequest(Method.GET);
                request.AddParameter("Content-Type", "application/json");
                IRestResponse response = client.Execute(request);
                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {

                    // LayerDTO json = new JavaScriptSerializer().Deserialize<LayerDTO>(response.Content);
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    output = JsonConvert.DeserializeObject<LayerDetailDto>(response.Content, settings);
                    return output;
                }
                else
                {
                    logger.Error("Errore recupero Dettaglio Layer: " + urlRequest + " response: " + response.StatusCode);
                    throw new UrbamidException(TipoErroreEnum.DettaglioLayer);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore recupero Dettaglio Layer: " + urlRequest + " Message: " + ex.Message + " stack: " + ex.StackTrace);
                throw new UrbamidException(TipoErroreEnum.DettaglioLayer);

            }
        }

        public CoverageDto getFeatureTypeRasterByNomeLayer(GetFeatureTypeDto input, NLog.Logger logger)
        {
            try
            {
                CoverageDto output = null;
                var client = new RestClient(input.link)
                {
                    Authenticator = new HttpBasicAuthenticator(input.username, input.password)
                };
                var request = new RestRequest(Method.GET);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {

                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore,
                        // Culture = CultureInfo.InvariantCulture,
                        FloatParseHandling = FloatParseHandling.Double
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    output = JsonConvert.DeserializeObject<CoverageDto>(response.Content, settings);
                    return output;
                }
                else
                {
                    logger.Error("Errore Get Feature RASTER " + input.link + " Status CODE: " + response.StatusCode);
                    throw new UrbamidException(TipoErroreEnum.FeatureRaster);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore Get Feature Raster " + input.link);
                throw new UrbamidException(TipoErroreEnum.FeatureRaster);
            }
        }

        public FeaturesTypeDto getFeatureTypeByNomeLayer(GetFeatureTypeDto input, NLog.Logger logger)
        {
            try
            {
                FeaturesTypeDto output = null;
                var client = new RestClient(input.link)
                {
                    Authenticator = new HttpBasicAuthenticator(input.username, input.password)
                };

                var request = new RestRequest(Method.GET);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {

                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    output = JsonConvert.DeserializeObject<FeaturesTypeDto>(response.Content, settings);
                    return output;
                }
                else
                {
                    logger.Error("Errore chiamata " + input.link + " in getFeatureTypeByNomeLayer  code: " + response.StatusCode);
                    throw new UrbamidException(TipoErroreEnum.FeatureLayer);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore getFeatureTypeByNomeLayer + " + ex.Message + " Stack: " + ex.StackTrace);
                throw new UrbamidException(TipoErroreEnum.FeatureLayer);

            }
        }

        public WmsFeatureDto getFeatureLayerWMS(GetFeatureTypeDto input, NLog.Logger logger)
        {
            try
            {
                WmsFeatureDto output = null;
                var client = new RestClient(input.link)
                {
                    Authenticator = new HttpBasicAuthenticator(input.username, input.password)
                };

                var request = new RestRequest(Method.GET);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {

                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    output = JsonConvert.DeserializeObject<WmsFeatureDto>(response.Content, settings);
                    return output;
                }
                else
                {
                    logger.Error("Errore chiamata " + input.link + " in getFeatureTypeByNomeLayer  code: " + response.StatusCode);
                    throw new UrbamidException(TipoErroreEnum.FeatureLayer);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore getFeatureTypeByNomeLayer + " + ex.Message + " Stack: " + ex.StackTrace);
                throw new UrbamidException(TipoErroreEnum.FeatureLayer);

            }
        }

        public bool UpdateLayer(EditLayerDto input, NLog.Logger logger)
        {
            try
            {

                string json = new JavaScriptSerializer().Serialize(new { input.layerDto.featureType });
                json = json.Replace("tipoPublished", "@type");
                json = json.Replace("null", "0");
                json = json.Replace("astratto", "abstract");
                json = json.Replace("chiave", "string");
                json = json.Replace("Classe", "@class");
                json = json.Replace("_space", "namespace");
                byte[] bytes = Encoding.UTF8.GetBytes(json);

                string urlRequest;

                if (!String.IsNullOrEmpty(input.linkFeature))
                    urlRequest = input.linkFeature;
                else
                    urlRequest = String.Concat(input.baseUrl, Costanti.REST_SERVICE_PREFIX, input.workspace,
                    "/datastores/", input.nomeLayer, "/featuretypes/", input.nomeLayer, ".json");



                WebRequest request = WebRequest.Create(urlRequest);
                request.ContentType = "application/json";
                request.Method = "PUT";
                request.Credentials = new NetworkCredential(input.username, input.password);
                request.GetRequestStream().Write(bytes, 0, bytes.Length);

                HttpWebResponse response = (HttpWebResponse)request.GetResponse();

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {

                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };


                    return true;
                }
                else
                {
                    logger.Error($"Update Layer {urlRequest} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.UpdateLayer);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Update Layer");
                throw new UrbamidException(TipoErroreEnum.UpdateLayer);

            }
        }

        public bool UpdateLayerGroup(CreateLayerGroupInDto layerGroup, NLog.Logger logger)
        {
            string gUrl = layerGroup.baseUrl + Costanti.REST_SERVICE_PREFIX + layerGroup.workspace + "/layergroups/" + layerGroup.name + ".json";
            try
            {

                string json = new JavaScriptSerializer().Serialize(new { layerGroup });

                json = json.Replace("tipoPublished", "@type");
                json = json.Replace("null", "\"null\"");
                json = json.Replace("chiave", "string");

                byte[] bytes = Encoding.UTF8.GetBytes(json);

                WebRequest request = WebRequest.Create(gUrl);
                request.ContentType = "application/json";
                request.Method = "PUT";
                request.Credentials = new NetworkCredential(layerGroup.username, layerGroup.password);
                request.GetRequestStream().Write(bytes, 0, bytes.Length);


                Stream requestStream = request.GetRequestStream();
                requestStream.Write(bytes, 0, bytes.Length);
                requestStream.Close();

                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                if (response.StatusCode == HttpStatusCode.OK)
                {
                    response.Close();
                    return true;
                }
                else
                {
                    logger.Error($"Errore Update Layer Group {gUrl} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.UpdateGroup);

                }

            }
            catch (Exception ex)
            {
                logger.Error(ex, "Update Layer Group");
                throw new UrbamidException(TipoErroreEnum.UpdateGroup);

            }
        }

        public bool deleteDataStore(DeleteDatastoreDto input, NLog.Logger logger)
        {
            String urlRequest = String.Empty;
            try
            {
                urlRequest = input.baseUrl + Costanti.REST_SERVICE_PREFIX + input.workspace + "/datastores/" + input.nomeDataStore + "?recurse=true";

                var client = new RestClient(urlRequest);
                client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                var request = new RestRequest(Method.DELETE);
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                    return deleteStyle(input, logger);
                else
                {
                    logger.Error($"Errore Delete Datastore {urlRequest} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.DeleteDataStore);
                }

            }
            catch (Exception ex)
            {
                logger.Error(ex, "Delete Datastore");
                throw new UrbamidException(TipoErroreEnum.DeleteDataStore);
            }
        }

        public bool deleteRaster(DeleteDatastoreDto input, NLog.Logger logger)
        {
            String urlRequest = String.Empty;
            try
            {
                urlRequest = input.baseUrl + Costanti.REST_SERVICE_PREFIX + input.workspace + "/coveragestores/" + input.nomeDataStore + "?recurse=true";

                var client = new RestClient(urlRequest);
                client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                var request = new RestRequest(Method.DELETE);
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                    return deleteStyle(input, logger);
                else
                {
                    logger.Error($"Errore Delete Datastore {urlRequest} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.DeleteRaster);
                }

            }
            catch (Exception ex)
            {
                logger.Error(ex, "Delete Raster");
                throw new UrbamidException(TipoErroreEnum.DeleteRaster);
            }
        }

        public bool deleteStyle(DeleteDatastoreDto input, NLog.Logger logger)
        {
            String urlRequest = String.Empty;
            try
            {

                //verifa se lo stile è già caricato
                GeoServerDto dto = new Dto.GeoServerDto();
                dto.baseUrl = input.baseUrl;
                dto.password = input.password;
                dto.username = input.username;
                dto.workspace = input.workspace;

                StyleDto stili = getStilifromWorkspace(dto, logger);
                if (stili != null && stili.styles != null && stili.styles.style != null)
                {
                    if (stili.styles.style.ToList().FindAll(p => p.name.Equals(input.nomeDataStore)).Count == 0)
                        return true;
                }
                else
                    return true;

                urlRequest = input.baseUrl + Costanti.REST_SERVICE_PREFIX + input.workspace + "/styles/" + input.nomeDataStore + "?recurse=true&purge=true";

                var client = new RestClient(urlRequest);
                client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                var request = new RestRequest(Method.DELETE);
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                    return true;
                else
                {
                    logger.Error($"Errore Delete Style {urlRequest} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.DeleteStyle);
                }

            }
            catch (Exception ex)
            {
                logger.Error(ex, "Delete Style");
                throw new UrbamidException(TipoErroreEnum.DeleteStyle);
            }
        }

        public bool deleteLayerGroup(DeleteDatastoreDto input, NLog.Logger logger)
        {
            String urlRequest = String.Empty;
            try
            {
                urlRequest = input.baseUrl + Costanti.REST_SERVICE_PREFIX + input.workspace + "/layergroups/" + input.nomeDataStore;

                var client = new RestClient(urlRequest);
                client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                var request = new RestRequest(Method.DELETE);
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                    return true;
                else
                {
                    logger.Error($"Errore Delete Group {urlRequest} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.DeleteGroup);
                }

            }
            catch (Exception ex)
            {
                logger.Error(ex, "Delete Group");
                throw new UrbamidException(TipoErroreEnum.DeleteGroup);
            }
        }

        //public bool createStyleInWorkspace(GeoServerDto input, NLog.Logger logger)
        //{
        //    try
        //    {

        //        string geoserverUrl = String.Format(input.baseUrl + Costanti.REST_SERVICE_PREFIX + input.workspace + "/styles.xml");
        //        var client = new RestClient(geoserverUrl);
        //        client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
        //        var request = new RestRequest(Method.POST);
        //        //string sldNameOnServer = String.Format("<style><name>{0}</name><filename>{0}.sld</filename></style>", getFileStyleName());
        //        string sldNameOnServer = String.Format("<style><name>{0}</name><filename>{0}.sld</filename></style>", input.layerName);
        //        request.AddHeader("Content-type", "text/xml");


        //        request.AddParameter("text/xml", sldNameOnServer, ParameterType.RequestBody);
        //        IRestResponse response = client.Execute(request);
        //        if (response.StatusCode == HttpStatusCode.Created || response.StatusCode == HttpStatusCode.OK)
        //            return true;

        //        else
        //        {
        //            logger.Error($"Errore Add Style To WorkSpace {geoserverUrl} Status Code {response.StatusCode}");
        //            throw new UrbamidException(TipoErroreEnum.AddStyleToWorkSpace);
        //        }
        //    }
        //    catch (Exception ex)
        //    {
        //        logger.Error(ex, "Errore Add Style To WorkSpace");
        //        throw new UrbamidException(TipoErroreEnum.AddStyleToWorkSpace);
        //    }


        //}

        public bool uploadStyleToWorkspace(GeoServerDto input, String nomeFileStyle, NLog.Logger logger)
        {

            string tempSLDfileName = String.Format("{0}.sld", nomeFileStyle);
            string tempSLDpath = Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "URBAMID", "SLD", tempSLDfileName);
            try
            {
                //verifico che non sia presente già uno stile con lo stesso nome
                string geoServer = $"{input.baseUrl}{Costanti.REST_SERVICE_PREFIX}{input.workspace}/styles";
                var client = new RestClient(geoServer);
                var request = new RestRequest(Method.GET);
                IRestResponse response = client.Execute(request);
                if (response.StatusCode != HttpStatusCode.OK)
                {
                    logger.Error($"Errore nel recuper degli stili dal WorkSpace {geoServer} Status Code: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.GetStyleFromWorkSpace);
                }
                else
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    StyleDto stili = JsonConvert.DeserializeObject<StyleDto>(response.Content, settings);
                    if (stili != null && stili.styles != null && stili.styles.style != null && stili.styles.style.Count > 0)
                    {
                        Style found = stili.styles.style.ToList().FindAll(x => x.name == nomeFileStyle).FirstOrDefault();
                        if (found != default(Style))
                        {

                            //cancellare lo stile
                            String deleteStyle = geoServer + "/" + nomeFileStyle + "?purge=true&recurse=true";
                            client = new RestClient(deleteStyle);
                            client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                            request = new RestRequest(Method.DELETE);

                            response = client.Execute(request);

                            if (response.StatusCode != HttpStatusCode.OK)
                            {
                                logger.Error($"Errore Cancellazione Stile da GeoServer {deleteStyle} StatusCode: {response.StatusCode}");
                            }
                        };


                    }
                }

                //UPLOAD SLD TO GEOSERVER
                client = new RestClient(geoServer);
                client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                request = new RestRequest(Method.POST);
                request.AddHeader("content-type", "application/vnd.ogc.sld+xml");
                request.AddParameter("application/vnd.ogc.sld+xml", File.ReadAllBytes(tempSLDpath), ParameterType.RequestBody);
                response = client.Execute(request);
                if (response.StatusCode == HttpStatusCode.Created)
                    return true;
                else
                {
                    logger.Error($"Errore Upload Style to WorkSpace {geoServer} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.UploadStyleToWorkSpace);
                }

            }
            catch (Exception ex)
            {
                logger.Error(ex, $"Errore Upload Style to WorkSpace");
                throw new UrbamidException(TipoErroreEnum.UploadStyleToWorkSpace);
            }

            finally
            {
                if (File.Exists(tempSLDpath))
                    File.Delete(tempSLDpath);
            }
        }

        public List<Layer> getListaLayerInWorkspace(GetListaLayerInDto input, NLog.Logger logger)
        {
            List<Layer> output = new List<Layer>();
            try
            {
                String urlRequest = input.baseUrl + Costanti.REST_SERVICE_PREFIX + input.workspace + "/layers.json";

                var client = new RestClient(urlRequest);
                client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                var request = new RestRequest(Method.GET);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {

                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    GetListaLayerOutDto res = JsonConvert.DeserializeObject<GetListaLayerOutDto>(response.Content, settings);
                    if (res != null && res.layers != null && res.layers.layer != null)
                    {
                        //foreach (Layer item in res.layers.layer)
                        //{
                        //    if (!item.name.ToUpper().StartsWith("DEFAULT"))
                        //        output.Add(item.name);
                        //}
                        output.AddRange(res.layers.layer);
                    }

                    return output;
                }
                else
                {
                    logger.Error($"Errore GetListaLayer in WorkSpace {urlRequest} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.LayersInWorkSpace);
                }

            }
            catch (Exception ex)
            {
                logger.Error(ex, $"Errore Get Lista Layer in WorkSpace");
                throw new UrbamidException(TipoErroreEnum.LayersInWorkSpace);
            }
        }

        public List<LayerGroup> getListaLayerGroupInWorkspace(GetListaLayerInDto input, NLog.Logger logger)
        {
            List<LayerGroup> output = new List<LayerGroup>();
            try
            {
                String urlRequest = input.baseUrl + Costanti.REST_SERVICE_PREFIX + input.workspace + "/layergroups.json";

                var client = new RestClient(urlRequest);
                client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                var request = new RestRequest(Method.GET);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {

                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    LayerGroupDto res = JsonConvert.DeserializeObject<LayerGroupDto>(response.Content, settings);
                    if (res != null && res.layerGroups != null && res.layerGroups.layerGroup != null)
                    {
                        output.AddRange(res.layerGroups.layerGroup);
                        //foreach (LayerGroup item in res.layerGroups.layerGroup)
                        //{
                        //    if(!item.name.ToUpper().StartsWith("DEFAULT"))
                        //       output.Add(item.name);
                        //}
                    }
                    return output;
                }
                else
                {

                    logger.Error($"Errore Get Groups in WorkSpace {urlRequest} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.GroupsInWorkSpace);
                }

            }
            catch (Exception ex)
            {
                logger.Error(ex, $"Errore Get Layer Group in WorkSpace");
                throw new UrbamidException(TipoErroreEnum.GroupsInWorkSpace);
            }
        }

        private bool applyStyleToLayer(GeoServerDto input, string nomeStile, NLog.Logger logger)
        {
            try
            {
                //string geoserverUrl3 = String.Format(input.baseUrl + Costanti.REST_SERVICE_PREFIX + "{0}/layers/{1}", input.workspace, input.storeName);
                string geoserverUrl3 = String.Format(input.baseUrl + "/rest/layers/{0}:{1}", input.workspace, input.layerName);
                var client3 = new RestClient(geoserverUrl3);
                client3.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                var request3 = new RestRequest(Method.PUT);
                string sldToBeAssigned = String.Format("<layer><defaultStyle><name>{0}</name><workspace>{1}</workspace></defaultStyle></layer>", nomeStile, input.workspace);
                request3.AddHeader("Content-type", "text/xml");
                request3.AddParameter("text/xml", sldToBeAssigned, ParameterType.RequestBody);
                IRestResponse response = client3.Execute(request3);

                if (response.StatusCode == HttpStatusCode.Created || response.StatusCode == HttpStatusCode.OK)
                    return true;
                else
                {
                    logger.Error($"Errore Apply Style To Layer {geoserverUrl3} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.StyleToLayer);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore Apply Style To Layer");
                throw new UrbamidException(TipoErroreEnum.StyleToLayer);
            }
        }


        public bool publishSLD(GeoServerDto input, string layerDaImportare, NLog.Logger logger)
        {
            ILayer selectedLayer = null;
            IDataset dataset = null;
            IFeatureLayer featureLayer = null;
            IGeoFeatureLayer geoFeatureLayer = null;
            IFeatureRenderer featureRenderer = null;
            Utility util = new Utility();
            try
            {
                ConvertLayerToSld converter = new ConvertLayerToSld();

                IMap map = ArcMap.Document.FocusMap;


                for (int index = 0; index < map.LayerCount; index++)
                {
                    if (map.Layer[index].Name == layerDaImportare)
                    {
                        selectedLayer = map.Layer[index];
                        break;
                    }
                }
                if (selectedLayer == null)
                {

                    util.message(TipoMessaggio.ERRORE, "Attenzione, selezionare il layer in ArcMap con lo stile che si vuole importare");
                    return false;

                }

                Boolean labelOpenOrClosed = getLabelStaus(layerDaImportare);
                String nomeFileStyle = getFileStyleName(layerDaImportare);
                nomeFileStyle = input.layerName;

                dataset = (IDataset)selectedLayer;
                featureLayer = (IFeatureLayer)selectedLayer;
                geoFeatureLayer = (IGeoFeatureLayer)featureLayer;
                featureRenderer = geoFeatureLayer.Renderer;
                bool isLayerDb = nomeFileStyle.StartsWith(Costanti.LAYERDB_PREFIX);

                if (geoFeatureLayer.Renderer is IUniqueValueRenderer)
                {
                    UrbamidAddIn_V10_2.Utility.ConvertLayerToSld.StructProject strutturaLayer = converter.getStrutturaLayer(geoFeatureLayer, featureLayer, isLayerDb);// new StructProject();
                    converter.saveLayertoTempPath(strutturaLayer, input.layerName);

                }
                else if (geoFeatureLayer.Renderer is ISimpleRenderer)
                {
                    //if (featureLayer.FeatureClass.ShapeType == ESRI.ArcGIS.Geometry.esriGeometryType.esriGeometryPoint ||
                    //    featureLayer.FeatureClass.ShapeType == ESRI.ArcGIS.Geometry.esriGeometryType.esriGeometryPolyline)
                    //{
                    try
                    {

                        UrbamidAddIn_V10_2.Utility.ConvertLayerToSld.StructProject strutturaLayer = converter.getStrutturaPoint(geoFeatureLayer, featureLayer, isLayerDb);// new StructProject();
                        converter.saveLayertoTempPath(strutturaLayer, nomeFileStyle);

                    }
                    catch (Exception)
                    {
                        try
                        {

                            String exportRes = ExportSLD(labelOpenOrClosed, nomeFileStyle);
                            if (String.IsNullOrEmpty(exportRes))
                            {
                                util.message(TipoMessaggio.ERRORE, "Si è verificato un errore nell'importazione");
                                return false;
                            }
                            else
                            {
                                SaveSLDtoTempPath(exportRes, nomeFileStyle);
                            }

                        }
                        catch (Exception ex2)
                        {
                            logger.Error(ex2, "Errore Upload Style ");
                            throw ex2;
                        }
                    }
                }
                else if (geoFeatureLayer.Renderer is IClassBreaksRenderer)
                {
                    UrbamidAddIn_V10_2.Utility.ConvertLayerToSld.StructClassBreaksRenderer strutturaLayer = converter.getStructBreacks((IClassBreaksRenderer)geoFeatureLayer.Renderer, featureLayer, isLayerDb);
                    converter.saveLayerBreaktoTempPath(strutturaLayer, nomeFileStyle);

                }
                else
                {
                    logger.Error($"Errore Style Non gestito");
                    throw new UrbamidException(TipoErroreEnum.StyleNonGestito);
                }




                //verifa se lo stile è già caricato
                StyleDto stili = getStilifromWorkspace(input, logger);
                if (stili == null)
                {
                    return false;
                }
                else if (stili.styles.style.Where(p => p.name.Equals(nomeFileStyle)).ToList().Count > 0)
                {

                    String messaggio = String.Format("Stile {0}, già presente in {1}. Si vuole sovrascrivere il vecchio stile?", nomeFileStyle, input.workspace);
                    if (MessageBox.Show(messaggio, "Attenzione", MessageBoxButtons.YesNo) == DialogResult.No)
                        return false;
                }

                if (!uploadStyleToWorkspace(input, nomeFileStyle, logger))
                {
                    logger.Error($"Errore Upload Style in WorkSpace");
                    throw new UrbamidException(TipoErroreEnum.PublishStyle);
                }


                return applyStyleToLayer(input, nomeFileStyle, logger);
            }
            catch (Exception ex)
            {
                logger.Error(ex, $"Errore Upload Style in WorkSpace");
                throw new UrbamidException(TipoErroreEnum.PublishStyle);

            }
        }

        public bool publishSLD(GeoServerDto input, string layerDaImportare, bool sovrascrivi, NLog.Logger logger)
        {
            ILayer selectedLayer = null;
            IDataset dataset = null;
            IFeatureLayer featureLayer = null;
            IGeoFeatureLayer geoFeatureLayer = null;
            IFeatureRenderer featureRenderer = null;
            Utility util = new Utility();
            try
            {
                ConvertLayerToSld converter = new ConvertLayerToSld();

                IMap map = ArcMap.Document.FocusMap;


                for (int index = 0; index < map.LayerCount; index++)
                {
                    if (map.Layer[index].Name == layerDaImportare)
                    {
                        selectedLayer = map.Layer[index];
                        break;
                    }
                }
                if (selectedLayer == null)
                {
                    util.message(TipoMessaggio.ERRORE, "Attenzione, selezionare il layer in ArcMap con lo stile che si vuole importare");
                    return false;

                }

                Boolean labelOpenOrClosed = getLabelStaus(layerDaImportare);
                String nomeFileStyle = getFileStyleName(layerDaImportare);
                nomeFileStyle = input.layerName;

                dataset = (IDataset)selectedLayer;
                featureLayer = (IFeatureLayer)selectedLayer;
                geoFeatureLayer = (IGeoFeatureLayer)featureLayer;
                featureRenderer = geoFeatureLayer.Renderer;
                bool isLayerDb = nomeFileStyle.StartsWith(Costanti.LAYERDB_PREFIX);

                if (geoFeatureLayer.Renderer is IUniqueValueRenderer)
                {
                    UrbamidAddIn_V10_2.Utility.ConvertLayerToSld.StructProject strutturaLayer = converter.getStrutturaLayer(geoFeatureLayer, featureLayer, isLayerDb);// new StructProject();
                    converter.saveLayertoTempPath(strutturaLayer, input.layerName);
                }
                else if (geoFeatureLayer.Renderer is ISimpleRenderer)
                {
                    //if (featureLayer.FeatureClass.ShapeType == ESRI.ArcGIS.Geometry.esriGeometryType.esriGeometryPoint ||
                    //    featureLayer.FeatureClass.ShapeType == ESRI.ArcGIS.Geometry.esriGeometryType.esriGeometryPolyline)
                    //{
                    try
                    {

                        UrbamidAddIn_V10_2.Utility.ConvertLayerToSld.StructProject strutturaLayer = converter.getStrutturaPoint(geoFeatureLayer, featureLayer, isLayerDb);// new StructProject();
                        converter.saveLayertoTempPath(strutturaLayer, nomeFileStyle);

                    }
                    catch (Exception)
                    {
                        try
                        {

                            String exportRes = ExportSLD(labelOpenOrClosed, nomeFileStyle);
                            if (String.IsNullOrEmpty(exportRes))
                            {
                                util.message(TipoMessaggio.ERRORE, "Si è verificato un errore nell'importazione");
                                return false;
                            }
                            else
                            {
                                SaveSLDtoTempPath(exportRes, nomeFileStyle);
                            }

                        }
                        catch (Exception ex2)
                        {
                            logger.Error(ex2, "Import Style publish SLD");
                            throw new UrbamidException(TipoErroreEnum.PublishStyle);

                        }
                    }
                }
                else if (geoFeatureLayer.Renderer is IClassBreaksRenderer)
                {
                    UrbamidAddIn_V10_2.Utility.ConvertLayerToSld.StructClassBreaksRenderer strutturaLayer = converter.getStructBreacks((IClassBreaksRenderer)geoFeatureLayer.Renderer, featureLayer, isLayerDb);
                    converter.saveLayerBreaktoTempPath(strutturaLayer, nomeFileStyle);

                }
                else
                {
                    message(TipoMessaggio.ERRORE, "Feature non supportata per la conversione");
                    return false;
                }




                //verifa se lo stile è già caricato
                StyleDto stili = getStilifromWorkspace(input, logger);
                if (stili == null)
                {
                    return false;
                }
                else if (stili.styles.style.Where(p => p.name.Equals(nomeFileStyle)).ToList().Count > 0)
                {

                    //String messaggio = String.Format("Stile {0}, già presente in {1}. Si vuole sovrascrivere il vecchio stile?", nomeFileStyle, input.workspace);
                    //if (MessageBox.Show(messaggio, "Attenzione", MessageBoxButtons.YesNo) == DialogResult.No)
                    if (!sovrascrivi)
                        return true;
                    else
                    {
                        //delete vecchio stile;
                        DeleteDatastoreDto dto = new DeleteDatastoreDto();
                        dto.baseUrl = input.baseUrl;
                        dto.nomeDataStore = nomeFileStyle;
                        dto.password = input.password;
                        dto.username = input.username;
                        dto.workspace = input.workspace;

                        if (!deleteStyle(dto, logger))
                        {
                            message(TipoMessaggio.ERRORE, "Errore cancellazione vecchio stile: " + nomeFileStyle);
                            return false;
                        }


                    }
                }


                //if (!createStyleInWorkspace(input, logger))
                //{
                //    message(TipoMessaggio.ERRORE, "Si è verificato in fase di creazione stile in WorkSpace: " + input.layerName);
                //    return false;
                //}

                if (!uploadStyleToWorkspace(input, nomeFileStyle, logger))
                {
                    message(TipoMessaggio.ERRORE, "Si è verificato in fase di upload del file: " + nomeFileStyle);
                    return false;
                }


                return applyStyleToLayer(input, nomeFileStyle, logger);
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Publish SLD");
                throw new UrbamidException(TipoErroreEnum.PublishStyle);

            }
        }

        

        private StyleDto getStilifromWorkspace(GeoServerDto input, NLog.Logger logger)
        {
            StyleDto output = null;
            String urlRequest = String.Concat(input.baseUrl, Costanti.REST_SERVICE_PREFIX, input.workspace,
                "/styles.json");
            var client = new RestClient(urlRequest);
            client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
            var request = new RestRequest(Method.GET);
            try
            {
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {

                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    output = JsonConvert.DeserializeObject<StyleDto>(response.Content, settings);


                    return output;
                }
                else
                {
                    logger.Error($"Errore GetStilifromWorkspace {urlRequest} Status Code  {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.GetStyleFromWorkSpace);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore GetStilifromWorkspace");
                throw new UrbamidException(TipoErroreEnum.GetStyleFromWorkSpace);

            }
        }


        public bool createLayerGroup(CreateLayerGroupInDto layerGroup, NLog.Logger logger)
        {

            string gUrl = layerGroup.baseUrl + Costanti.REST_SERVICE_PREFIX + layerGroup.workspace + "/layergroups";
            try
            {

                string json = new JavaScriptSerializer().Serialize(new { layerGroup });

                json = json.Replace("tipoPublished", "@type");
                json = json.Replace("null", "\"null\"");
                json = json.Replace("chiave", "string");


                // XNode node = JsonConvert.DeserializeXNode(json, "");
                byte[] bytes = Encoding.UTF8.GetBytes(json);
                //byte[] bytes = Encoding.UTF8.GetBytes(node.ToString());

                WebRequest request = WebRequest.Create(gUrl);

                request.ContentType = "application/json";
                request.Method = "POST";
                request.Credentials = new NetworkCredential(layerGroup.username, layerGroup.password);


                request.GetRequestStream().Write(bytes, 0, bytes.Length);


                Stream requestStream = request.GetRequestStream();
                requestStream.Write(bytes, 0, bytes.Length);
                requestStream.Close();

                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                if (response.StatusCode == HttpStatusCode.Created)
                {
                    response.Close();
                    return true;
                }
                else
                {
                    logger.Error($"Errore Create Layer Group {gUrl} Status Code  {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.CreateLayerGroup);
                }

            }
            catch (Exception ex)
            {
                logger.Error(ex, "Create Layer Group");
                throw new UrbamidException(TipoErroreEnum.CreateLayerGroup);

            }
        }


        //private char[] getAddToLayerGroupXml(CreateLayerGroupInDto input)
        //{
        //    string templateGroup = String.Empty;

        //    templateGroup = "<layerGroup><name>{0}</name><mode>SINGLE</mode><title>{1}</title><abstractTxt>{2}</abstractTxt><workspace><name>{3}</name></workspace><publishables><published type='layer'><name>urbamid:APF_2007</name><link>www.google.it</link></published></publishables><styles><style><name>default_style</name><link>www.facebook.com</link></style></styles><bounds><minx>0</minx><maxx>0</maxx><miny>0</miny><maxy>0</maxy><crs>EPSG:4326</crs></bounds><keywords><keyword>defaultView\\@vocabulary=true\\;</keyword></keywords></layerGroup>";
        //    templateGroup = String.Format(templateGroup, input.nome, input.titolo, input.titolo, input.workspace);

        //    return templateGroup.ToCharArray();
        //}

        #region metodi privati

        private byte[] readLocalShapeFile(string filePath, NLog.Logger logger)
        {
            byte[] buffer;
            FileStream fStream = new FileStream(filePath, FileMode.Open, FileAccess.Read);
            try
            {
                int length = (int)fStream.Length;
                buffer = new byte[length];
                int count;
                int sum = 0;

                // Read until Read method returns 0 - End of stream reached
                while ((count = fStream.Read(buffer, sum, length - sum)) > 0)
                    sum += count;
            }
            catch (Exception ex)
            {
                logger.Error(ex, "ReadLocalShapeFile");
                throw new UrbamidException(TipoErroreEnum.ReadShapeFile);

            }
            finally
            {
                fStream.Close();
            }

            return buffer;
        }

        #endregion

        #region WRAPPER
        public UploadShape_OutDto UploadShapeToDB(UploadShape_InDto dto, NLog.Logger logger)
        {
            String server = dto.baseUrl;
            UploadShape_OutDto output = new UploadShape_OutDto();
            try
            {
                server = String.Concat(server, "/", "storage/singolo");
                var client = new RestClient(server);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-Type", "multipart/form-data"); //
                request.AddParameter("overwrite", dto.overwrite.ToString());
                request.AddFile("file", dto.pathFile, " application/zip"); //"application/x-zip-compressed");


                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    UploadShapeDb_Service resp = JsonConvert.DeserializeObject<UploadShapeDb_Service>(response.Content, settings);
                    output = WrapperTOA.assembler(resp);

                }
                else
                {
                    logger.Error($"Errore Ulpoad Shape To Db {server} Status Code  {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.UploadShapeToDB);
                }


                return output;
            }
            catch (Exception ex)
            {
                logger.Error(ex, $"Exception Upload Shape DB");
                throw new UrbamidException(TipoErroreEnum.UploadShapeToDB);
            }
        }

        public GetDettaglioLayer_OutDto getDettaglioLayer(GetDettaglioLayer_InDto inDto, NLog.Logger logger)
        {
            String server = inDto.baseUrl;
            GetDettaglioLayer_OutDto outDto = new GetDettaglioLayer_OutDto();
            try
            {
                server = String.Concat(inDto.baseUrl, "/geoserver/layer");
                var client = new RestClient(server);
                var request = new RestRequest(Method.GET);
                request.AddParameter("layer", inDto.nomeLayer);
                request.AddParameter("workspaces", inDto.workspace);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    GetLayer_Service output = JsonConvert.DeserializeObject<GetLayer_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);

                }
                else
                {
                    logger.Error($"Errore Dettaglio Layer {server} STATUS CODE: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.DettaglioLayer);
                }

                return outDto;
            }
            catch (Exception ex)
            {
                logger.Error(ex, $"Errore Dettaglio Layer {server}");
                throw new UrbamidException(TipoErroreEnum.DettaglioLayer);
            }
        }

        public ConvertShapeToDb_OutDto convertShapeToDb(ConvertShapeToDb_InDto inDto, NLog.Logger logger)
        {
            String server = String.Concat(inDto.baseUrl, "/shapeToDb/converter?nameShapeFile={0}");
            ConvertShapeToDb_OutDto outDto = new ConvertShapeToDb_OutDto();
            try
            {

                server = String.Format(server, inDto.nomeLayer);
                var client = new RestClient(server);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-Type", "application/json");
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    ConvertToLayer_Service output = JsonConvert.DeserializeObject<ConvertToLayer_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);
                    return outDto;
                }
                else
                {
                    logger.Error($"Errore convertShapeToDb: {server} STATUS CODE: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.ConvertShapeToDB);
                }



            }
            catch (Exception ex)
            {

                logger.Error(ex, "Error Convert Shape To Db");
                throw new UrbamidException(TipoErroreEnum.ConvertShapeToDB);
            }
        }


        public PublishTable_OutDto publishTable(PublishTable_InDto inDto, NLog.Logger logger)
        {
            String server = String.Concat(inDto.baseUrl, "/geoserver/publishFT");
            PublishTable_OutDto outDto = new PublishTable_OutDto();
            try
            {
                var client = new RestClient(server);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-Type", "application/json");
                request.AddParameter("workspaces", inDto.workspace);
                request.AddParameter("datastores", inDto.dataStore);
                request.AddParameter("name", inDto.nomeLayer);
                request.AddParameter("title", inDto.titoloLayer);
                String keywords = String.Format("shapeDb={0};estrazionePart={1};defaultView={2}", inDto.shapeToDb, inDto.estrazionePart, inDto.defaultView);
                request.AddParameter("keywords", keywords);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    PublishTable_Service output = JsonConvert.DeserializeObject<PublishTable_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);
                }
                else
                {
                    logger.Error($"Errore Publish FT {server}, STATUS CODE: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.PublishTableLayer);
                }


                return outDto;

            }
            catch (Exception ex)
            {
                logger.Error(ex, $"Errore publishFT {server}");
                throw new UrbamidException(TipoErroreEnum.PublishTableLayer);
            }
        }

        public AddLayerToGroup_OutDto AddLayerToGroup(AddLayerToGroup_InDto inDto, NLog.Logger logger)
        {

            String server = String.Concat(inDto.baseUrl, "/geoserver/addLayerToLayergroup");
            AddLayerToGroup_OutDto outDto = new AddLayerToGroup_OutDto();
            try
            {



                var client = new RestClient(server);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-Type", "application/json");
                request.AddParameter("workspaces", inDto.workspace);
                request.AddParameter("layergroup", inDto.nomeGruppo);
                request.AddParameter("layer", inDto.nomeLayer);
                request.AddParameter("style", inDto.stile);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    AddLayerToGroup_Service output = JsonConvert.DeserializeObject<AddLayerToGroup_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);
                    return outDto;
                }
                else
                {
                    logger.Error($"Errore Add Layer to Group {server}, STATUS CODE: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.AddLayerToGroup);
                }
            }


            catch (Exception ex)
            {
                logger.Error(ex, "ADD LAYER TO LAYER GROUP");
                throw new UrbamidException(TipoErroreEnum.AddLayerToGroup);
            }
        }


        public GetFeatureByLayer_OutDto getFeatureByLayer(GetFeatureByLayer_InDto inDto, NLog.Logger logger)
        {

            String server = String.Concat(inDto.baseUrl, "/geoserver/featureType");
            GetFeatureByLayer_OutDto outDto = new GetFeatureByLayer_OutDto();
            try
            {



                var client = new RestClient(server);
                var request = new RestRequest(Method.GET);
                request.AddHeader("Content-Type", "application/json");

                request.AddParameter("workspaces", inDto.workspace);
                request.AddParameter("featuretype", inDto.featureType);
                request.AddParameter("datastore", inDto.featureType);
                //request.AddParameter("featureTypeDb", inDto.isFeatureDb);


                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    GetFeatureTypeLayer_Service output = JsonConvert.DeserializeObject<GetFeatureTypeLayer_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);
                }
                else
                {
                    logger.Error($"Errore GetFeatureByLayer  {server} Status Code  {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.FeatureLayer);
                }


                return outDto;

            }
            catch (Exception ex)
            {
                logger.Error(ex, "Get Feature By Layer");
                throw new UrbamidException(TipoErroreEnum.FeatureLayer);
            }
        }

        public DeleteLayer_OutDto deleteLayer(DeleteLayer_InDto inDto, NLog.Logger logger)
        {
            String server = String.Concat(inDto.baseUrl, "/geoserver/delLayer");
            DeleteLayer_OutDto outDto = new DeleteLayer_OutDto();
            try
            {



                var client = new RestClient(server);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-Type", "application/json");
                request.AddParameter("workspaces", inDto.workspace);
                request.AddParameter("layer", inDto.nomeLayer);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    DeleteLayer_Service output = JsonConvert.DeserializeObject<DeleteLayer_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);
                    return outDto;
                }
                else
                {
                    logger.Error($"Errore Delete Layer Wrapper {server} Status Code  {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.DeleteLayer);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, $"Errore Delete Layer Wrapper");
                throw new UrbamidException(TipoErroreEnum.DeleteLayer);
            }
        }


        public DeleteStyle_OutDto deleteStyle(DeleteStyle_InDto inDto, NLog.Logger logger)
        {
            String server = String.Concat(inDto.baseUrl, "/geoserver/delStyle");
            DeleteStyle_OutDto outDto = new DeleteStyle_OutDto();
            try
            {
                var client = new RestClient(server);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-Type", "application/json");
                request.AddParameter("workspaces", inDto.workspace);
                request.AddParameter("style", inDto.nomeStile);
                request.AddParameter("sldFile", inDto.nomeStile);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    DeleteStyle_Service output = JsonConvert.DeserializeObject<DeleteStyle_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);
                    return outDto;
                }
                else
                {
                    logger.Error($"Errore Delete Style Wrapper {server} Status Code  {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.DeleteStyle);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Delete Style Wrapper");
                throw new UrbamidException(TipoErroreEnum.DeleteStyle);
            }
        }

        public EditFeature_OutDto editFeatureType(EditFeature_InDto inDto, NLog.Logger logger)
        {
            String server = String.Concat(inDto.baseUrl, "/geoserver/modFeatureType");
            EditFeature_OutDto outDto = new EditFeature_OutDto();
            try
            {



                var client = new RestClient(server);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-Type", "application/json");
                request.AddParameter("workspaces", inDto.workspace);
                request.AddParameter("featuretype", inDto.nomeFeature);
                request.AddParameter("title", inDto.title);
                request.AddParameter("keywords", inDto.keywords);


                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    EditFeature_Service output = JsonConvert.DeserializeObject<EditFeature_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);
                    return outDto;
                }
                else
                {
                    logger.Error($"Errore Edit Feature Type {server} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.UpdateFeatureLayer);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Edit Feature Type Wrapper");
                throw new UrbamidException(TipoErroreEnum.UpdateFeatureLayer);
            }
        }

        public GetListaLayers_OutDto getListaLayers(GetListaLayers_InDto inDto, NLog.Logger logger)
        {
            String server = String.Concat(inDto.baseUrl, "/geoserver/layers");
            GetListaLayers_OutDto outDto = new GetListaLayers_OutDto();
            try
            {
                var client = new RestClient(server);
                var request = new RestRequest(Method.GET);
                request.AddHeader("Content-Type", "application/json");
                request.AddParameter("workspaces", inDto.workspace);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    GetListaLayers_Service output = JsonConvert.DeserializeObject<GetListaLayers_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);
                }
                else
                {
                    logger.Error($"Errore Get Lista Layer Style {server} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.ListaLayers);
                }


                return outDto;

            }
            catch (Exception ex)
            {
                logger.Error(ex, $"Errore Get Lista Layer Wrapper");
                throw new UrbamidException(TipoErroreEnum.ListaLayers);
            }
        }

        public GetListaLayerGroups_OutDto getListaLayerGroups(GetListaLayerGroups_InDto inDto, NLog.Logger logger)
        {
            String server = String.Concat(inDto.baseUrl, "/geoserver/layergroups");
            GetListaLayerGroups_OutDto outDto = new GetListaLayerGroups_OutDto();
            try
            {
                var client = new RestClient(server);
                var request = new RestRequest(Method.GET);
                request.AddHeader("Content-Type", "application/json");
                request.AddParameter("workspaces", inDto.workspace);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    GetListaLayerGroups_Service output = JsonConvert.DeserializeObject<GetListaLayerGroups_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);

                    return outDto;
                }
                else
                {
                    logger.Error($"Errore get Lista Gruppi Wrapper {server} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.ListaGruppi);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Exception Get Lista Gruppi Wrapper");
                throw new UrbamidException(TipoErroreEnum.ListaGruppi);
            }
        }
        public GetDettaglioGruppo_OutDto getDettaglioGruppo(GetDettaglioGruppo_InDto inDto, NLog.Logger logger)
        {
            String server = String.Concat(inDto.baseUrl, "/geoserver/layergroup");
            GetDettaglioGruppo_OutDto outDto = new GetDettaglioGruppo_OutDto();
            try
            {
                var client = new RestClient(server);
                var request = new RestRequest(Method.GET);
                request.AddHeader("Content-Type", "application/json");
                request.AddParameter("workspaces", inDto.workspace);
                request.AddParameter("layergroup", inDto.nomeGruppo);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    GetDettaglioGruppo_Service output = JsonConvert.DeserializeObject<GetDettaglioGruppo_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);
                    return outDto;
                }
                else
                {
                    logger.Error($"Errore Dettaglio Gruppo Wrapper {server} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.DettaglioGruppo);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Exception Dettaglio Gruppo Wrapper");
                throw new UrbamidException(TipoErroreEnum.DettaglioGruppo);
            }
        }


        public DeleteFeature_OutDto deleteFeatureType(DeleteFeature_InDto inDto, NLog.Logger logger)
        {
            String server = String.Concat(inDto.baseUrl, "/geoserver/delFeatureType");
            DeleteFeature_OutDto outDto = new DeleteFeature_OutDto();
            try
            {
                var client = new RestClient(server);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-Type", "application/json");
                request.AddParameter("workspaces", inDto.workspace);
                request.AddParameter("feturetype", inDto.nomeFeatureType);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    DeleteFeature_Service output = JsonConvert.DeserializeObject<DeleteFeature_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);
                    return outDto;
                }
                else
                {
                    logger.Error($"Errore Delete Feature Type {server} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.DeleteFeatureLayer);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Delete Feature Type Wrapper");
                throw new UrbamidException(TipoErroreEnum.DeleteFeatureLayer);
            }
        }

        public GetFeatureByLayer_OutDto getFeatureByLayerDS(GetFeatureByLayer_InDto inDto, NLog.Logger logger)
        {
            String server = String.Concat(inDto.baseUrl, "/geoserver/featureType");
            GetFeatureByLayer_OutDto outDto = new GetFeatureByLayer_OutDto();
            try
            {

                var client = new RestClient(server);
                var request = new RestRequest(Method.GET);
                request.AddHeader("Content-Type", "application/json");

                request.AddParameter("workspaces", inDto.workspace);
                request.AddParameter("featuretype", inDto.featureType);
                request.AddParameter("datastores", inDto.featureType);
                //request.AddParameter("featureTypeDb", inDto.isFeatureDb);


                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    GetFeatureTypeLayer_Service output = JsonConvert.DeserializeObject<GetFeatureTypeLayer_Service>(response.Content, settings);
                    outDto = WrapperTOA.assembler(output);
                    return outDto;
                }
                else
                {
                    logger.Error($"Errore Get Feature Layer  {server} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.FeatureLayer);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Get Feature Layer Wrapper");
                throw new UrbamidException(TipoErroreEnum.FeatureLayer);
            }
        }

        internal bool ExistStoreWMS(WMSStoreDto inputDto, NLog.Logger logger)
        {
            String restGeoServer = $"{inputDto.baseUrl}{Costanti.REST_SERVICE_PREFIX}{inputDto.workspace}/wmsstores";
            try
            {

                var client = new RestClient(restGeoServer);
                var request = new RestRequest(Method.GET);
                request.AddHeader("Content-Type", "application/json");
                client.Authenticator = new HttpBasicAuthenticator(inputDto.username, inputDto.password);
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    WmsStoreDTO items = JsonConvert.DeserializeObject<WmsStoreDTO>(response.Content, settings);
                    if (items != null && items.WmsStores != null && items.WmsStores.WmsStore != null)
                    {
                        int count = items.WmsStores.WmsStore.FindAll(x => x.Name.ToUpper() == inputDto.storeName.ToUpper()).Count;

                        return count > 0;
                    }
                    return false;
                }
                else
                {
                    logger.Error($"Errore Get WMS STORE {restGeoServer} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.CheckWMSSTore);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore Get WMS STORE");
                throw new UrbamidException(TipoErroreEnum.CheckWMSSTore);
            }
        }

        internal void AddStoreWMS(WMSStoreDto inputDto, Logger logger)
        {
            String restGeoServer = $"{inputDto.baseUrl}{Costanti.REST_SERVICE_PREFIX}{inputDto.workspace}/wmsstores";
            try
            {

                var client = new RestClient(restGeoServer);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-type", "text/xml");
                String wmsStore = $"<wmsStore><name>{inputDto.storeName}</name><type>WMS</type><capabilitiesURL>{inputDto.linkWMS}</capabilitiesURL><workspace>urbamid</workspace></wmsStore>";
                client.Authenticator = new HttpBasicAuthenticator(inputDto.username, inputDto.password);
                request.AddParameter("text/xml", wmsStore, ParameterType.RequestBody);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode != HttpStatusCode.Created)
                {
                    logger.Error($"Errore Add WMS STORE {restGeoServer} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.AddWMSSTore);

                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore Add WMS STORE");
                throw new UrbamidException(TipoErroreEnum.AddWMSSTore);
            }
        }

        internal List<TipologicaDTO> GetLayersFromWMS(WMSStoreDto inputDto, Logger logger)
        {
            String wmsGetCapabilities = $"{inputDto.linkWMS}&service=wms&request=getCapabilities";
            List<TipologicaDTO> output = new List<TipologicaDTO>();
            TipologicaDTO tipo = new TipologicaDTO()
            {
                Titolo = "Seleziona tutti",
                Nome = "SelectALL"
            };
            output.Add(tipo);
            try
            {

                var client = new RestClient(wmsGetCapabilities);
                var request = new RestRequest(Method.GET);
                IRestResponse response = client.Execute(request);

                if (response.StatusCode != HttpStatusCode.OK)
                {
                    logger.Error($"Errore GET CAPATIBILITIES WMS STORE {wmsGetCapabilities} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.LayersFromWMS);

                }
                else
                {
                    var xmlResponse = XElement.Parse(response.Content); // Parse the response
                    string prefix = "{http://www.opengis.net/wms}";
                    string elementLayer = $"{prefix}Layer";

                    List<XElement> layers = xmlResponse.DescendantNodes().OfType<XElement>().ToList().Where(x => x.Name == elementLayer).ToList();
                    if (layers != null && layers.Count > 0)
                    {
                        string elementTitle = $"{prefix}Title";
                        string elementName = $"{prefix}Name";
                        foreach (XElement item in layers)
                        {
                            tipo = new TipologicaDTO();
                            var nomeLayer = item.DescendantNodes().OfType<XElement>().ToList().Where(x => x.Name == elementName).FirstOrDefault();
                            if (nomeLayer != null && nomeLayer != default(XElement))
                                tipo.Nome = nomeLayer.Value;

                            var titoloLayer = item.DescendantNodes().OfType<XElement>().ToList().Where(x => x.Name == elementTitle).FirstOrDefault();
                            if (titoloLayer != null && titoloLayer != default(XElement))
                                tipo.Titolo = titoloLayer.Value;

                            if (!String.IsNullOrEmpty(tipo.Nome) && !String.IsNullOrEmpty(tipo.Titolo))
                                output.Add(tipo);
                        }
                    }
                }

                return output;
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore AGET CAPATIBILITIES");
                throw new UrbamidException(TipoErroreEnum.LayersFromWMS);
            }
        }

        internal void AddLayerWMS(LayerWMSDto inputDto, Logger logger)
        {
            String restGeoServer = $"{inputDto.baseUrl}{Costanti.REST_SERVICE_PREFIX}{inputDto.workspace}/wmsstores/{inputDto.storeName}/wmslayers/";

            try
            {

                var client = new RestClient(restGeoServer);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-type", "text/xml");
                String wmsLayers = $"<wmsLayer><name>{inputDto.nomeLayer}</name></wmsLayer>";
                client.Authenticator = new HttpBasicAuthenticator(inputDto.username, inputDto.password);
                request.AddParameter("text/xml", wmsLayers, ParameterType.RequestBody);

                IRestResponse response = client.Execute(request);

                if (response.StatusCode != HttpStatusCode.Created)
                {
                    logger.Error($"Errore Add WMS LAYER {restGeoServer} Status Code {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.ADDWMSLayer);

                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore Add WMS LAYER");
                throw new UrbamidException(TipoErroreEnum.ADDWMSLayer);
            }
        }

        internal void ReprojectLayer(LayerWMSDto layerDto, Logger logger)
        {

            try
            {
                String urlRest = $"{layerDto.baseUrl}/rest/workspaces/{layerDto.workspace}/wmslayers/{layerDto.nomeLayer}";
                var client = new RestClient(urlRest);
                var request = new RestRequest(Method.GET);
                client.Authenticator = new HttpBasicAuthenticator(layerDto.username, layerDto.password);
                IRestResponse response = client.Execute(request);

                if (response.StatusCode != HttpStatusCode.OK)
                {
                    logger.Error($"Errore GET Dettaglio Layer Reproject {layerDto.linkWMS} Status CODE: {response.StatusCode}");
                    return;
                }
                var settings = new JsonSerializerSettings
                {
                    NullValueHandling = NullValueHandling.Ignore,
                    MissingMemberHandling = MissingMemberHandling.Ignore
                };

                response.Content = response.Content.Replace("\"null\"", "");
                WmsFeatureDto dettaglio = JsonConvert.DeserializeObject<WmsFeatureDto>(response.Content, settings);
                dettaglio.wmsLayer.srs = "EPSG:4326";
                dettaglio.wmsLayer.projectionPolicy = "REPROJECT_TO_DECLARED";

                String urlUpdate = $"{urlRest}?recalculate";
                client = new RestClient(urlRest)
                {
                    Authenticator = new HttpBasicAuthenticator("admin", "geoserver")
                };
                request = new RestRequest(Method.PUT);
                request.AddHeader("Content-Type", "application/json");
                request.AddParameter("application/json", JsonConvert.SerializeObject(dettaglio), ParameterType.RequestBody);
                response = client.Execute(request);


                if (response.StatusCode != HttpStatusCode.OK)
                {
                    logger.Error($"Errore EDIT Dettaglio Layer Reproject {layerDto.linkWMS} Status CODE: {response.StatusCode}");
                    return;
                }

            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore Riproiezione WMS LAYER");
                return;
            }

        }

        public List<TipologicaDTO> GetStoreWms(BaseDto dtoBase, Logger logger)
        {
            String urlWmsStore = $"{dtoBase.baseUrl}/rest/workspaces/{dtoBase.workspace}/wmsstores";
            try
            {

                List<TipologicaDTO> output = new List<TipologicaDTO>();
                TipologicaDTO tipo = new TipologicaDTO()
                {
                    Titolo = "Seleziona tutti",
                    Nome = "SelectALL"
                };
                output.Add(tipo);


                var client = new RestClient(urlWmsStore);
                var request = new RestRequest(Method.GET);
                request.AddHeader("Content-type", "application/json");
                client.Authenticator = new HttpBasicAuthenticator(dtoBase.username, dtoBase.password);
                IRestResponse response = client.Execute(request);

                if (response.StatusCode != HttpStatusCode.OK)
                {
                    logger.Error($"Errore Recupero WMS STORES {urlWmsStore} Status CODE: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.GetStoreWMS);
                }
                else
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    StoreWmsDto stores = JsonConvert.DeserializeObject<StoreWmsDto>(response.Content, settings);
                    if (stores != null && stores.WmsStore != null && stores.WmsStore.WmsStore != null)
                    {
                        foreach (StoreWms item in stores.WmsStore.WmsStore)
                        {
                            TipologicaDTO store = new TipologicaDTO
                            {
                                Nome = item.Href,
                                Titolo = item.Name
                            };
                            output.Add(store);
                        }
                    }

                    return output;
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore Recupero WMS STORES");
                throw new UrbamidException(TipoErroreEnum.GetStoreWMS);
            }
        }

        internal void DeleteStoreWMS(BaseDto dtoBase, string nomeStore, Logger logger)
        {
            String urlWmsStore = $"{dtoBase.baseUrl}/rest/workspaces/{dtoBase.workspace}/wmsstores/{nomeStore}?recurse=true";
            try
            {

                var client = new RestClient(urlWmsStore);
                var request = new RestRequest(Method.DELETE);
                request.AddHeader("Content-type", "application/json");
                client.Authenticator = new HttpBasicAuthenticator(dtoBase.username, dtoBase.password);
                IRestResponse response = client.Execute(request);

                if (response.StatusCode != HttpStatusCode.OK)
                {
                    logger.Error($"Errore Cancellazione WMS STORES {urlWmsStore} Status CODE: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.DeleteStoreWMS);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore Cancellazione WMS STORES");
                throw new UrbamidException(TipoErroreEnum.DeleteStoreWMS);
            }
        }

        internal bool deleteLayerWMS(DeleteDatastoreDto dto, String nomeGruppo, Logger logger)
        {
            String urlWmsStore = $"{dto.baseUrl}/rest/workspaces/{dto.workspace}/wmslayers/{dto.nomeDataStore}?recurse=true";
            String urlGruppo = $"{dto.baseUrl}/rest/workspaces/{dto.workspace}/layergroups/{nomeGruppo}";
            try
            {

                // RECUPERO IL GRUPPO PER ELIMINARE IL LAYER
                var client = new RestClient(urlGruppo);
                var request = new RestRequest(Method.GET);
                request.AddHeader("Content-type", "application/json");
                client.Authenticator = new HttpBasicAuthenticator(dto.username, dto.password);
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };
                    response.Content = response.Content.Replace("\"null\"", "");
                    GruppoDto gruppo = JsonConvert.DeserializeObject<GruppoDto>(response.Content, settings);
                    if (gruppo != null && gruppo.layerGroup != null)
                    {
                        String nomeL = $"{dto.workspace}:{dto.nomeDataStore}";
                        Published item = gruppo.layerGroup.publishables.published.ToList().FindAll(x => x.name == nomeL).FirstOrDefault();
                        if (item != default(Published))
                        {
                            int index = gruppo.layerGroup.publishables.published.ToList().FindIndex(x => x.name == nomeL);
                            gruppo.layerGroup.publishables.published.Remove(item);

                            for (int i = 0; i < gruppo.layerGroup.styles.style.Count(); i++)
                            {
                                if (gruppo.layerGroup.styles.style[i] != null && i == index)
                                {
                                    gruppo.layerGroup.styles.style.Remove(gruppo.layerGroup.styles.style[i]);
                                    break;
                                }
                            }

                            //MODIFICO IL GRUPPO
                            client = new RestClient(urlGruppo)
                            {
                                Authenticator = new HttpBasicAuthenticator(dto.username, dto.password)
                            };
                            request = new RestRequest(Method.PUT);
                            request.AddHeader("Content-Type", "application/json");
                            request.AddParameter("application/json", JsonConvert.SerializeObject(gruppo), ParameterType.RequestBody);
                            response = client.Execute(request);
                            if (response.StatusCode != HttpStatusCode.OK)
                            {
                                logger.Error($"Errore EDIT GRUPPO {urlGruppo} STATUS_CODE: {response.StatusCode}");
                                throw new UrbamidException(TipoErroreEnum.UpdateGroup);

                            }
                        }
                    }

                    //POSSO FINALMENTE ELIMINARE IL LAYER
                    client = new RestClient(urlWmsStore)
                    {
                        Authenticator = new HttpBasicAuthenticator(dto.username, dto.password)
                    };
                    request = new RestRequest(Method.DELETE);
                    request.AddHeader("Content-Type", "application/json");
                    response = client.Execute(request);
                    if (response.StatusCode == HttpStatusCode.OK)
                        return true;
                    else
                    {
                        logger.Error($"Errore DELETE LAYER WNS {urlGruppo} STATUS_CODE: {response.StatusCode}");
                        throw new UrbamidException(TipoErroreEnum.DeleteLayerWMS);

                    }
                }
                else
                {
                    logger.Error($"Errore recupero gruppo {urlGruppo} STATUS_CODE: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.DettaglioGruppo);
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore Cancellazione LAYER WMS");
                throw new UrbamidException(TipoErroreEnum.DeleteLayerWMS);
            }

        }

        internal void UpdateLayerWMS(GetFeatureTypeDto dto, string nomeLayer, WmsFeatureDto featureWMS, Logger logger)
        {
            String urlRest = $"{dto.baseUrl}/rest/workspaces/{dto.workspace}/wmslayers/{nomeLayer}?recalculate";
            try
            {

                // RECUPERO IL GRUPPO PER ELIMINARE IL LAYER
                var client = new RestClient(urlRest);
                var request = new RestRequest(Method.PUT);
                request.AddHeader("Content-type", "application/json");
                request.AddParameter("application/json", JsonConvert.SerializeObject(featureWMS), ParameterType.RequestBody);
                client.Authenticator = new HttpBasicAuthenticator(dto.username, dto.password);
                IRestResponse response = client.Execute(request);

                if (response.StatusCode != HttpStatusCode.OK)
                {
                    logger.Error($"Errore EDIT LAYER WMS {urlRest} STATUS_CODE: {response.StatusCode}");
                    throw new UrbamidException(TipoErroreEnum.UpdateLayerWMS);
                }
              
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Errore EDIT LAYER WMS");
                throw new UrbamidException(TipoErroreEnum.UpdateLayerWMS);
            }
        }
        #endregion
    }
}
