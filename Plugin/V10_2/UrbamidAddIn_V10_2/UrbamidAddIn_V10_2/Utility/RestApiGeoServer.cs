using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.Geodatabase;
using Newtonsoft.Json;
using RestSharp;
using RestSharp.Authenticators;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using System.Windows.Forms;
using UrbamidAddIn_V10_2.Dto;

namespace UrbamidAddIn_V10_2.Utility
{
    public class RestApiGeoServer : Utility
    {
        public LayerGroupDto getLayergroups(GetGroupLayerDto dto)
        {
         
            LayerGroupDto output = null;
            String restUrl = dto.baseUrl;
            restUrl += Costanti.REST_SERVICE_PREFIX + dto.workspace + "/layergroups.json";
            var client = new RestSharp.RestClient(restUrl);
            client.Authenticator = new HttpBasicAuthenticator(dto.username, dto.password);
            var request = new RestSharp.RestRequest(Method.GET);
            try
            {
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
                    message(TipoMessaggio.ERRORE, "Errore connessione GeoServer");
                    return null;
                }
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore nel recupero del LayerGroup:" + dto.nomeNodo);
                scriviLog(ex.StackTrace);
                return null;

            }
        }
        
        public bool UploadRaster(UploadRasterDto input)
        {
            String server = String.Empty;
            try
            {
                String extension = String.IsNullOrEmpty(input.extension) ? ".geotiff" : input.extension;

                //File.WriteAllBytes(@"C:\TEMP\" + input.nome , input.arrFile);
                //byte[] localShapeFile = readLocalShapeFile(@"C:\TEMP\" + input.nome);
                server = String.Concat(input.baseUrl, Costanti.REST_SERVICE_PREFIX,
                    input.workspace, "/coveragestores/", Path.GetFileNameWithoutExtension(input.nome), "/file", extension ,"?filename=" + input.nome);
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
                {
                    response.Close();
                    return true;
                }
                else
                {
                    scriviLog(response.StatusDescription);
                    response.Close();
                    return false;
                }
            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                return false;

            }


        }

        /// <summary>
        /// Upload un file con estensione .zip 
        /// Crea DataStore e Layer
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public bool UploadShapeFile(GeoServerDto input)
        {
            String server = String.Empty;
            try
            {
                server = String.Concat(input.baseUrl, Costanti.REST_SERVICE_PREFIX,
                    input.workspace, "/datastores/", input.storeName, "/file.shp?configure=all&update=overwrite&filename=", input.layerName);

                byte[] localShapeFile = readLocalShapeFile(input.pathFile);
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
                    return true;
                }
                else
                {
                    scriviLog(response.StatusDescription);
                    response.Close();
                    return false;
                }
            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                return false;

            }


        }

        /// <summary>
        /// Riproietta il file con EPSG:4326
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public Boolean EditSrs(GeoServerDto input)
        {
            String urlRequest = String.Empty;
           

            String body = "<featureType><srs>EPSG:4326</srs><projectionPolicy>REPROJECT_TO_DECLARED</projectionPolicy></featureType>";
            byte[] bytes = Encoding.UTF8.GetBytes(body);
            try
            {
                if(String.IsNullOrEmpty(input.linkDettaglio))
                urlRequest = String.Concat(input.baseUrl, Costanti.REST_SERVICE_PREFIX, input.workspace,
                    "/datastores/", input.layerName, "/featuretypes/", input.storeName, "?recalculate=nativebbox,latlonbbox");
                else
                    urlRequest = String.Concat(input.linkDettaglio, "?recalculate=nativebbox,latlonbbox"); 

                WebRequest request = WebRequest.Create(urlRequest);
                request.ContentType = "application/xml";
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
                    message(TipoMessaggio.ERRORE, "Errore edit riproiezione layer");
                    scriviLog(response.StatusDescription);
                    response.Close();
                    return false;
                }
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore modifica riproiezione Layer");
                scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
                return false;

            }
        }


        public Boolean EditSrsRaster(CoverageDto input, BaseDto dtoBase)
        {

            input.coverage.latLonBoundingBox.crs = "EPSG:4326";
            input.coverage.projectionPolicy = "REPROJECT_TO_DECLARED";
            input.coverage.enabled = true;
           // input.coverage.metadata = input.coverage.metadata;
            String urlRequest = String.Empty;
            string json = new JavaScriptSerializer().Serialize(new { input.coverage });
            json = json.Replace("tipoPublished", "@type");
            //json = json.Replace("null", "\"null\"");
            json = json.Replace("###", "null");
            json = json.Replace("chiave", "string");
            json = json.Replace("NameSpaceValue", "namespace");
            json = json.Replace("Classe", "@class");
            json = json.Replace("Valore", "$");
            json = json.Replace("RequestSrs", "string");
            json = json.Replace("ResponseSrs", "string");
            json = json.Replace("SupportedFormatsValues", "string");
            json = json.Replace("Valori", "$");
            json = json.Replace("InterpolationMethodsValues", "string");
            json = json.Replace("EntryKey", "@key");
            json = json.Replace("GridDimension", "@dimension");



            byte[] bytes = Encoding.UTF8.GetBytes(json);


            try
            {

                urlRequest = String.Concat(dtoBase.baseUrl, Costanti.REST_SERVICE_PREFIX, dtoBase.workspace,
                    "/coveragestores/", Path.GetFileNameWithoutExtension(input.coverage.name), "/coverages/", Path.GetFileNameWithoutExtension(input.coverage.name), "?recalculate=nativebbox,latlonbbox");
                WebRequest request = WebRequest.Create(urlRequest);
                request.ContentType = "application/json";
                request.Method = "PUT";
                request.Credentials = new NetworkCredential(dtoBase.username, dtoBase.password);
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
                    message(TipoMessaggio.ERRORE, "Errore edit riproiezione layer");
                    scriviLog(response.StatusDescription);
                    response.Close();
                    return false;
                }
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore modifica riproiezione Layer");
                scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
                return false;

            }
        }



        /// <summary>
        /// Agginge un layer esistente ad un layer group
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public Boolean addLayerToGroup(LayerDTO input)
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
                    scriviLog(response.StatusDescription);
                    response.Close();
                    return false;
                }
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore aggiunta layer a LayerGroup");
                scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
                return false;

            }
        }
                

        public LayerDTO getLayergroupsByName(GetGroupLayerDto dto)
        {
            LayerDTO output = null;
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
            try
            {
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
                    response.Content = response.Content.Replace("\"Null\"", "");
                    response.Content = response.Content.Replace("\"NULL\"", "");

                    output = JsonConvert.DeserializeObject<LayerDTO>(response.Content, settings);
                    return output;
                }
                else
                {
                    message(TipoMessaggio.ERRORE, "Errore nel recupero del LayerGroup");
                    return null;
                }
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore nel recupero del LayerGroup:" + dto.nomeNodo);
                scriviLog(ex.StackTrace);
                return null;

            }
        }

        public LayerDetailDto getDettaglioLayer(GeoServerDto input)
        {
            String urlRequest = String.Empty;
            try
            {
                LayerDetailDto output = null;
                if (String.IsNullOrEmpty(input.linkDettaglio))
                    urlRequest = input.baseUrl + Costanti.REST_SERVICE_PREFIX + input.workspace + "/layers/" + input.storeName + ".json";
                else
                    urlRequest = input.linkDettaglio;

                var client = new RestClient(urlRequest);
                client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                var request = new RestRequest(Method.GET);
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
                    scriviLog("URL: " + urlRequest + "--> " + response.StatusCode.ToString());
                    return null;
                }
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore nel recupero del Dettaglio Layer:" + input.layerName);
                scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
                return null;
            }
        }

        public CoverageDto getFeatureTypeRasterByNomeLayer(GetFeatureTypeDto input)
        {
            CoverageDto output = null;
            var client = new RestClient(input.link);
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
                    scriviLog("ERRORE REQUEST: " + response.Content);
                    return null;
                }
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore nel recupero delle Features:" + input.link);
                scriviLog("URL: " + input.link + "--> " + ex.StackTrace);
                return null;

            }
        }

        public FeaturesTypeDto getFeatureTypeByNomeLayer(GetFeatureTypeDto input)
        {
            FeaturesTypeDto output = null;
            var client = new RestClient(input.link);
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
                    output = JsonConvert.DeserializeObject<FeaturesTypeDto>(response.Content, settings);
                    return output;
                }
                else
                {
                    scriviLog("ERRORE REQUEST: " + response.Content);
                    return null;
                }
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore nel recupero delle Features:" + input.link);
                scriviLog("URL: " + input.link + "--> " + ex.StackTrace);
                return null;

            }
        }

        public bool UpdateLayer(EditLayerDto input)
        {
            string json = new JavaScriptSerializer().Serialize(new { input.layerDto.featureType});
            json = json.Replace("tipoPublished", "@type");
            json = json.Replace("null", "0");
            json = json.Replace("astratto", "abstract");
            json = json.Replace("chiave", "string");
            json = json.Replace("Classe", "@class");
            json = json.Replace("_space", "namespace");
            byte[] bytes = Encoding.UTF8.GetBytes(json);

            String urlRequest = String.Empty;

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

            try
            {
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
                    message(TipoMessaggio.ERRORE, "Errore nell'update del layer");
                    return false;
                }
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore nell'update del layer: " + input.nomeLayer);
                scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
                return false;

            }
        }

        internal bool UpdateLayerGroup(CreateLayerGroupInDto layerGroup)
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
                    message(TipoMessaggio.WARNING, "Si è verificato un problema!");
                    scriviLog(response.StatusDescription);
                    response.Close();
                    return false;
                }

            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, String.Format("Errore nell'aggiornamento del nodo: {0} - {1}", ex.Message, ex.StackTrace));
                scriviLog(String.Format("Errore chiamata: {0} - Dettagli: {1} Stacktrace: {2}", gUrl, ex.Message, ex.StackTrace));
                return false;

            }
        }

        internal bool deleteDataStore(DeleteDatastoreDto input)
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
                    return deleteStyle(input);
                else
                {
                    message(TipoMessaggio.ERRORE, "Errore nella cancellazione del layer: " + input.nomeDataStore + " - " + response.StatusCode.ToString());
                    return false;
                }

            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore nella cancellazione del layer:" + input.nomeDataStore);
                scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
                return false;
            }
        }

        internal bool deleteRaster(DeleteDatastoreDto input)
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
                    return deleteStyle(input);
                else
                {
                    message(TipoMessaggio.ERRORE, "Errore nella cancellazione del layer: " + input.nomeDataStore + " - " + response.StatusCode.ToString());
                    return false;
                }

            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore nella cancellazione del layer:" + input.nomeDataStore);
                scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
                return false;
            }
        }

        internal bool deleteStyle(DeleteDatastoreDto input)
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

                StyleDto stili = getStilifromWorkspace(dto);
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
                    message(TipoMessaggio.ERRORE, "Errore nella cancellazione stile: " + input.nomeDataStore + " - " + response.StatusCode.ToString());
                    return false;
                }

            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore nella cancellazione del layer:" + input.nomeDataStore);
                scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
                return false;
            }
        }

        internal bool deleteLayerGroup(DeleteDatastoreDto input)
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
                    message(TipoMessaggio.ERRORE, "Errore nella cancellazione del Gruppo: " + input.nomeDataStore + " - " + response.StatusCode.ToString());
                    return false;
                }

            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore nella cancellazione del Gruppo:" + input.nomeDataStore);
                scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
                return false;
            }
        }

        public bool createStyleInWorkspace(GeoServerDto input)
        {
            try
            {

                string geoserverUrl = String.Format(input.baseUrl + Costanti.REST_SERVICE_PREFIX + input.workspace + "/styles.xml");
                var client = new RestClient(geoserverUrl);
                client.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                var request = new RestRequest(Method.POST);
                //string sldNameOnServer = String.Format("<style><name>{0}</name><filename>{0}.sld</filename></style>", getFileStyleName());
                string sldNameOnServer = String.Format("<style><name>{0}</name><filename>{0}.sld</filename></style>", input.layerName);
                request.AddHeader("Content-type", "text/xml");
              
                
                request.AddParameter("text/xml", sldNameOnServer, ParameterType.RequestBody);
                IRestResponse response = client.Execute(request);
                if (response.StatusCode == HttpStatusCode.Created || response.StatusCode == HttpStatusCode.OK)
                    return true;

                else
                {
                    scriviLog("ERRORE STILE: " + input.layerName + ":" +response.StatusCode + "--> " + response.Content);
                    return true;
                }
            }
            catch (Exception ex)
            {
                scriviLog("Exception STILE: " + input.layerName + " --> " + ex.Message);
                scriviLog(ex.StackTrace);
                return false;
            }


        }

        public bool uploadStyleToWorkspace(GeoServerDto input, String nomeFileStyle)
        {
            string tempSLDfileName = String.Format("{0}.sld", nomeFileStyle);
            //string tempSLDpath = System.IO.Path.Combine(System.IO.Path.GetTempPath(), tempSLDfileName);

            string tempSLDpath = Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "URBAMID","SLD", tempSLDfileName);

            try
            {


                var sldToBeUploaded = File.ReadAllBytes(tempSLDpath);
                
                string geoserverUrl2 = String.Format(input.baseUrl + Costanti.REST_SERVICE_PREFIX + "{0}/styles/{1}", input.workspace, nomeFileStyle);
                var client2 = new RestClient(geoserverUrl2);
                client2.Authenticator = new HttpBasicAuthenticator(input.username, input.password);
                var request2 = new RestRequest(Method.PUT);
                request2.AddHeader("Content-type", "application/vnd.ogc.sld+xml");
                request2.AddParameter("application/vnd.ogc.sld+xml", sldToBeUploaded, ParameterType.RequestBody);
                scriviLog("FILE Da Uplodare: " + tempSLDpath);
                scriviLog("Dimensione File: " + sldToBeUploaded.Length);
                scriviLog("GeoServer URL: " + geoserverUrl2);
                IRestResponse response = client2.Execute(request2);
                if (response.StatusCode == HttpStatusCode.Created || response.StatusCode == HttpStatusCode.OK)
                    return true;
                else
                {
                    scriviLog("FILE: " + tempSLDpath + "   ERR: "+ response.StatusCode + "--> " + response.ErrorMessage);
                    return false;
                }

            }
            catch (Exception ex)
            {
                scriviLog("NOME FILE: " + nomeFileStyle  + "  EXC: " + ex.Message);
                return false;
            }

            finally
            {
                if (File.Exists(tempSLDpath))
                    File.Delete(tempSLDpath);
            }
        }

        internal List<Layer> getListaLayerInWorkspace(GetListaLayerInDto input)
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

                }
                return output;
            }
            catch (Exception ex)
            {
                scriviLog(String.Format("Messaggio: {0} - Stack: {1}", ex.Message, ex.StackTrace));
                message(TipoMessaggio.ERRORE, "Si è verificato un errore nel recupero dei layers");
                return output;
            }
        }

        internal List<LayerGroup> getListaLayerGroupInWorkspace(GetListaLayerInDto input)
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

                }
                return output;
            }
            catch (Exception ex)
            {
                scriviLog(String.Format("Messaggio: {0} - Stack: {1}", ex.Message, ex.StackTrace));
                message(TipoMessaggio.ERRORE, "Si è verificato un errore nel recupero dei layers");
                return output;
            }
        }
    
        private bool applyStyleToLayer(GeoServerDto input, string nomeStile)
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
                    message(TipoMessaggio.ERRORE, "Errore apply Style to Layer ");
                    scriviLog(response.StatusCode + "--> " + response.ErrorMessage);
                    return false;
                }
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore apply Style to Layer");
                scriviLog(ex.StackTrace);
                return false;
            }
        }

        public bool publishSLD(GeoServerDto input)
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
                if (ArcMap.Document.SelectedLayer == null)
                {
                    util.message(TipoMessaggio.ERRORE, "Attenzione, selezionare il layer in ArcMap con lo stile che si vuole importare");
                    return false;

                }

                Boolean labelOpenOrClosed = getLabelStaus();
                String nomeFileStyle = getFileStyleName();
                nomeFileStyle = input.layerName;
                         

                selectedLayer = ArcMap.Document.SelectedLayer;
                dataset = (IDataset)selectedLayer;
                featureLayer = (IFeatureLayer)selectedLayer;
                geoFeatureLayer = (IGeoFeatureLayer)featureLayer;
                featureRenderer = geoFeatureLayer.Renderer;
                bool isLayerDb = nomeFileStyle.StartsWith(Costanti.LAYERDB_PREFIX);

                if (geoFeatureLayer.Renderer is IUniqueValueRenderer)
                {
                    UrbamidAddIn_V10_2.Utility.ConvertLayerToSld.StructProject strutturaLayer = converter.getStrutturaLayer(geoFeatureLayer, featureLayer, isLayerDb);// new StructProject();
                    converter.saveLayertoTempPath(strutturaLayer, input.layerName);
                    scriviLog("IUniqueValueRenderer");

                }
                else if (geoFeatureLayer.Renderer is ISimpleRenderer)
                {
                    scriviLog("ISimpleRenderer");
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
                            util.scriviLog(ex2.Message + "--> " + ex2.StackTrace);
                            util.message(TipoMessaggio.ERRORE, "Si è verificato un errore nell'importazione " + ex2.Message);
                            return false;
                        }
                    }
                    //}
                    //else
                    //{

                    //    String exportRes = ExportSLD(labelOpenOrClosed, nomeFileStyle);
                    //    if (String.IsNullOrEmpty(exportRes))
                    //        return false;

                    //    SaveSLDtoTempPath(exportRes, nomeFileStyle);
                    //}
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
                StyleDto stili = getStilifromWorkspace(input);
                if (stili == null)
                {
                    return false;
                }
                else if (stili.styles.style.Where(p => p.name.Equals(nomeFileStyle)).ToList().Count > 0)
                {

                    String messaggio = String.Format("Stile {0}, già presente in {1}. Si vuole sovrascrivere il vecchio stile?", nomeFileStyle, input.workspace);

                    if (MessageBox.Show(messaggio, "Attenzione", MessageBoxButtons.YesNo) == DialogResult.No)
                        return false;
                    else
                    {
                        //delete vecchio stile;
                        DeleteDatastoreDto dto = new DeleteDatastoreDto();
                        dto.baseUrl = input.baseUrl;
                        dto.nomeDataStore = nomeFileStyle;
                        dto.password = input.password;
                        dto.username = input.username;
                        dto.workspace = input.workspace;

                        if (!deleteStyle(dto))
                        {
                            message(TipoMessaggio.ERRORE, "Errore cancellazione vecchio stile: " + nomeFileStyle);
                            return false;
                        }


                    }
                }


                if (!createStyleInWorkspace(input))
                {
                    message(TipoMessaggio.ERRORE, "Si è verificato in fase di creazione stile in WorkSpace: " + input.layerName);
                    return false;
                }

                if (!uploadStyleToWorkspace(input, nomeFileStyle))
                {
                    message(TipoMessaggio.ERRORE, "Si è verificato in fase di upload del file: " + nomeFileStyle);
                    return false;
                }
                 

                return applyStyleToLayer(input, nomeFileStyle);
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore pubblicazione file SLD");
                scriviLog(ex.StackTrace);
                return false;

            }
        }


        private StyleDto getStilifromWorkspace(GeoServerDto input)
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
                    message(TipoMessaggio.ERRORE, String.Format("Errore nel recupero degli stili: {0} ", response.Content));
                    return null;
                }
            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, String.Format("Errore nel recupero degli stili: {0} - {1}", ex.Message, ex.StackTrace));
                scriviLog("URL: " + urlRequest + "--> " + ex.StackTrace);
                return null;

            }
        }


        public bool createLayerGroup(CreateLayerGroupInDto layerGroup)
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
                    message(TipoMessaggio.WARNING, "Si è verificato un problema!");
                    scriviLog(response.StatusDescription);
                    response.Close();
                    return false;
                }

            }
            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, String.Format("Errore crezione Gruppo: {0} - {1}", ex.Message, ex.StackTrace));
                scriviLog(String.Format("Errore chiamata: {0} - Dettagli: {1} Stacktrace: {2}", gUrl, ex.Message, ex.StackTrace));
                return false;

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

        private byte[] readLocalShapeFile(string filePath)
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
                scriviLog(ex.StackTrace);
                return null;

            }
            finally
            {
                fStream.Close();
            }

            return buffer;
        }

        #endregion

        #region WRAPPER
        public UploadShape_OutDto UploadShapeToDB(UploadShape_InDto dto)
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
                    scriviLog(response.StatusDescription);
                    output.IsSuccess = false;
                    output.Message = response.ErrorMessage;
                }
               

                return output;
            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                output.IsSuccess = false;
                output.Message = ex.Message;
                return output;
            }
        }

        public GetDettaglioLayer_OutDto getDettaglioLayer(GetDettaglioLayer_InDto inDto)
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
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }

                return outDto;
            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }

        public ConvertShapeToDb_OutDto convertShapeToDb(ConvertShapeToDb_InDto inDto)
        {
            String server = String.Concat(inDto.baseUrl, "/shapeToDb/converter?nameShapeFile={0}");
            ConvertShapeToDb_OutDto outDto = new ConvertShapeToDb_OutDto();
            try
            {
              
                server = String.Format(server, inDto.nomeLayer );
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
                }
                else
                {
                    scriviLog("URL: " + server + "--> " + response.Content);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }

                return outDto;

            }
            catch (Exception ex)
            {

                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }


        public PublishTable_OutDto publishTable(PublishTable_InDto inDto)
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
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }


                return outDto;

            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }

        public AddLayerToGroup_OutDto addLayerToGroup(AddLayerToGroup_InDto inDto)
        {

                String server =String.Concat(inDto.baseUrl, "/geoserver/addLayerToLayergroup");
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
                }
                else
                {
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }


                return outDto;

            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }


        public GetFeatureByLayer_OutDto getFeatureByLayer(GetFeatureByLayer_InDto inDto)
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
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }


                return outDto;

            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }

        public DeleteLayer_OutDto deleteLayer(DeleteLayer_InDto inDto)
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
                }
                else
                {
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }


                return outDto;

            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }


        public DeleteStyle_OutDto deleteStyle(DeleteStyle_InDto inDto)
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
                }
                else
                {
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }


                return outDto;

            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }

        public EditFeature_OutDto editFeatureType(EditFeature_InDto inDto)
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
                }
                else
                {
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }


                return outDto;

            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }

        public GetListaLayers_OutDto getListaLayers(GetListaLayers_InDto inDto)
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
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }


                return outDto;

            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }

        public GetListaLayerGroups_OutDto getListaLayerGroups(GetListaLayerGroups_InDto inDto)
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
                }
                else
                {
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }


                return outDto;

            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }


        public GetDettaglioGruppo_OutDto getDettaglioGruppo(GetDettaglioGruppo_InDto inDto)
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
                }
                else
                {
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }


                return outDto;

            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }


        internal DeleteFeature_OutDto deleteFeatureType(DeleteFeature_InDto inDto)
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
                }
                else
                {
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }


                return outDto;

            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }
        #endregion

        
        internal GetFeatureByLayer_OutDto getFeatureByLayerDS(GetFeatureByLayer_InDto inDto)
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
                }
                else
                {
                    scriviLog(response.StatusDescription);
                    outDto.IsSuccess = false;
                    outDto.Message = response.ErrorMessage;
                }


                return outDto;

            }
            catch (Exception ex)
            {
                scriviLog("URL: " + server + "--> " + ex.StackTrace);
                outDto.IsSuccess = false;
                outDto.Message = ex.Message;
                return outDto;
            }
        }
    }
}
