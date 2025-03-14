using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Security.Principal;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel;
using System.Runtime.InteropServices;
using UrbamidAddIn_V10_2.Dto;
using UrbamidAddIn_V10_2.Utility;
using RestSharp;
using RestSharp.Authenticators;



namespace testClient
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                //RestApiGeoServer service = new RestApiGeoServer();

                if (uploadStyleToWorkspace())
                  Console.WriteLine("SUCCESS...");
              else
                  Console.WriteLine("EPIC FAIL...");

               //if(uploadShapeOpenSit())
               //{
               //    if (EditLayerDto())
               //        Console.WriteLine("SUCCESS...");
               //    else
               //        Console.WriteLine("Errore EDIT-->");

                   Console.ReadKey();
                       return;
                //GetFeatureByLayer_InDto input = new GetFeatureByLayer_InDto();
                //input.baseUrl = @"http://192.168.11.222:8084/urbamid/wrappergeo/rest/api";
                //input.workspace = "urbamid";
                //input.featureType = "u_geo_apf_2017";

                //List<String> chiaviNormalizzate = new List<string>();

                //GetFeatureByLayer_OutDto outDettaglio = service.getFeatureByLayer(input);
                //if (outDettaglio != null && outDettaglio.IsSuccess)
                //{
                //    List<string> lsKeys = new List<string>();

                //    foreach (string chiave in outDettaglio.feature.keywords.chiave)
                //    {
                //        String nuovaChiave = chiave;
                //        nuovaChiave = nuovaChiave.Replace("\\", "");
                //        nuovaChiave.Replace("@vocabulary=", "@");
                //        lsKeys.Add(nuovaChiave);

                //    }



            
               



                //#region Upload File Catasto OPEN SIT
                //if (uploadShapeOpenSit())
                //{
                //    Console.WriteLine("SUCCESS...");
                //    Console.ReadKey();
                //    return;
                //}
                //else
                //{
                //    Console.WriteLine("FAIL...");
                //    Console.ReadKey();
                //    return;
                //}


                //#endregion

                #region Get Dettaglio LAYER 
                if (getDettaglioLayerWrapper())
                {
                    Console.WriteLine("SUCCESS...");
                    Console.ReadKey();
                    return;
                }
                else
                {
                    Console.WriteLine("FAIL...");
                    Console.ReadKey();
                    return;
                }
                #endregion


                //#region Upload File REST
                //if (uploadFileRestSERVICE())
                //{
                //    Console.WriteLine("SUCCESS...");
                //    Console.ReadKey();
                //    return;
                //}
                //else
                //{
                //    Console.WriteLine("FAIL...");
                //    Console.ReadKey();
                //    return;
                //}


                //#endregion

                #region PublishTableToLayer
                if (publishTable())
                {
                    Console.WriteLine("SUCCESS...");
                    Console.ReadKey();
                    return;
                }
                else
                {
                    Console.WriteLine("FAIL...");
                    Console.ReadKey();
                    return;
                }
                #endregion



                //#region ConvertShapeToTable
                //if (convertShapeToTable())
                //{
                //    Console.WriteLine("SUCCESS...");
                //    Console.ReadKey();
                //    return;
                //}
                //else
                //{
                //    Console.WriteLine("FAIL...");
                //    Console.ReadKey();
                //    return;
                //}
                //#endregion


            

                //#region Test Upload GEOSERVER
                //if (uploadTIFFonGeoServer())
                //    Console.WriteLine("OK");
                //else
                //    Console.WriteLine("KO");

                //return;

                //#endregion


                string dir = "\\\\192.168.11.222\\smbuser";
                dirsearch(dir);
          
              //  FtpWebRequest request = (FtpWebRequest)WebRequest.Create("ftp://192.168.11.222/opt/");
                FtpWebRequest ftpRequest = (FtpWebRequest)WebRequest.Create("ftp://192.168.11.222/");
                
                //ftpRequest.AuthenticationLevel = System.Net.Security.AuthenticationLevel.None;
                //ftpRequest.ImpersonationLevel = TokenImpersonationLevel.Anonymous;
                //ftpRequest.UsePassive = true;
                
                
                ftpRequest.Credentials = new NetworkCredential("smbuser", "smbpassword");
                ftpRequest.Method = WebRequestMethods.Ftp.ListDirectory;
                
               FtpWebResponse response = (FtpWebResponse)ftpRequest.GetResponse();
                StreamReader streamReader = new StreamReader(response.GetResponseStream());

                List<string> directories = new List<string>();

                string line = streamReader.ReadLine();
                while (!string.IsNullOrEmpty(line))
                {
                    directories.Add(line);
                    line = streamReader.ReadLine();
                }

                streamReader.Close();

            }
            catch (Exception ex)
            {
                
                throw ex;
            }
        }

        private static bool EditLayerDto()
        {
            try
            {
                 // AGGIORNO LE FEATURE NEL VECCHIO MODO
                RestApiGeoServer restService = new RestApiGeoServer();
                GetFeatureTypeDto inGetFeature = new GetFeatureTypeDto();
                String server = @"http://192.168.11.50:8080/geoserver";
                String works = "comune";
                String nomeLayer = "ZTO2";
                String password = "Geoserver";
                String username = "admin";
                inGetFeature.link = String.Concat(server, Costanti.REST_SERVICE_PREFIX, works,
                    "/datastores/", nomeLayer, "/featuretypes/", nomeLayer, ".json");

                //VECCHIA CHIAMATA ALLE FEATURE


                //WrapperTOA.assemblaGetFeature(this.WrapperUrl, this.DefaultWorkSpace, dto.layerName, false);
                FeaturesTypeDto outGetFeature = restService.getFeatureTypeByNomeLayer(inGetFeature);

                if (outGetFeature == null || outGetFeature.featureType == null)
                {
                   Console.WriteLine( "Errore nel recupero della Feature!");
                    return false;
                }
                List<string> attributtiFissi = outGetFeature.featureType.keywords.chiave.ToList();
                attributtiFissi.RemoveAll(p => p.Contains("@"));

                outGetFeature.featureType.keywords.chiave = attributtiFissi;
                outGetFeature.featureType.keywords.chiave.Add(String.Format("defaultView@{0}", "false"));
                //Aggiungo le properties in visualizzazione
                foreach (var item in outGetFeature.featureType.attributes.attribute)
                {
                    if (!item.binding.ToUpper().Contains("GEOMETRY") && !item.name.ToUpper().Equals("THE_GEOM"))
                    {
                        String keywords = string.Empty;

                        keywords += "viewField_" + item.name + "@" + item.name;

                        outGetFeature.featureType.keywords.chiave.Add(keywords);
                    }
                }
                outGetFeature.featureType.enabled = true;
                outGetFeature.featureType.title = nomeLayer;
                outGetFeature.featureType.astratto = nomeLayer;


                outGetFeature.featureType.srs = "EPSG:4326";
                outGetFeature.featureType.projectionPolicy = "REPROJECT_TO_DECLARED";
               
                EditLayerDto editDTO = new EditLayerDto();
                editDTO.baseUrl = server;
                editDTO.password = password;
                editDTO.username = username;
                editDTO.workspace = works;
                editDTO.layerDto = outGetFeature;
                editDTO.nomeLayer = nomeLayer;



                if (!restService.UpdateLayer(editDTO))
                {
                    Console.WriteLine("Update Layer");
                    return false;
                }

                return true;
            }
            catch (Exception ex)
            {
                Console.WriteLine("Errore" + ex.Message );
                throw ex;
            }
        }

        private static bool getDettaglioLayerWrapper()
        {
            try
            {
                Console.WriteLine("Publicazione iniziata...");

                String nomeLayer = "PIPO";
                String work = "urbamid";
                String server = @"http://192.168.11.222:8084/urbamid/wrappergeo/rest/api/geoserver/layer";
                server = String.Concat(server, "?layer=", nomeLayer, "&workspaces=", work);
                var client = new RestClient(server);
                var request = new RestRequest(Method.GET);
                request.AddHeader("Content-Type", "application/json");
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    // var pippo = JsonConvert.DeserializeObject(response.Content);
                    return true;
                }



                return false;

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return false;
            }
        }

        private static bool uploadShapeOpenSit()
        {
            try
            {
                RestApiGeoServer restService = new RestApiGeoServer();
                GeoServerDto inDTO = new GeoServerDto();
                inDTO.baseUrl = @"http://192.168.11.50:8080/geoserver";
                inDTO.password = "Geoserver";
                inDTO.username = "admin";
                inDTO.pathFile = @"C:\Users\cainghilte\Downloads\ZTO2.zip";
                inDTO.workspace = "comune";


                inDTO.storeName = "ZTO2";
                inDTO.layerName = "ZTO2";
                if (restService.UploadShapeFile(inDTO))
                    return true;
                else
                    return false;




            }
            catch (Exception ex)
            {

                Console.WriteLine("ERROR:..." + ex.Message);
                return false;
            }
        }

        private static bool publishTable()
        {
            try
            {
                Console.WriteLine("Publicazione iniziata...");


                String server = @"http://192.168.11.222:8084/urbamid/wrappergeo/rest/api/geoserver/publishFT";
                server = String.Format(server, "apf_2009");
                var client = new RestClient(server);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-Type", "application/json");
                request.AddParameter("workspaces", "urbamid");
                request.AddParameter("datastores", "dbUrbamid");
                request.AddParameter("name", "apf_2009");
                request.AddParameter("title", "TITOLO Area percorso dal fuoco 2009");

                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    // var pippo = JsonConvert.DeserializeObject(response.Content);
                    return true;
                }



                return false;

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return false;
            }
        }

        private static bool convertShapeToTable()
        {
            try
            {
                Console.WriteLine("Conversione iniziata...");


                String server = @"http://192.168.11.222:8084/urbamid/wrappergeo/rest/api/shapeToDb/converter?nameShapeFile={0}";
                server = String.Format(server, "APF_2007");
                var client = new RestClient(server);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-Type", "application/json"); 
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    // var pippo = JsonConvert.DeserializeObject(response.Content);
                    return true;
                }



                return false;

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return false;
            }
        }

        private static bool uploadFileRestSERVICE()
        {
            try
            {
                Console.WriteLine("Caricamento iniziato...");
                 String server = @"http://192.168.11.222:8084/urbamid/wrappergeo/rest/api/storage/singolo";
                //String server = @"http://161.27.61.115:8084/urbamid/wrappergeo/rest/api/storage/singolo";
                
                var client = new RestClient(server);
                var request = new RestRequest(Method.POST);
                request.AddHeader("Content-Type", "multipart/form-data"); //
                request.AddParameter("overwrite", "false");
                request.AddFile("file", "C:\\Temp\\APF_2009.zip"," application/zip"); //"application/x-zip-compressed");
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == HttpStatusCode.OK)
                {
                    return true;
                }
                
                

                return false;

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return false;
            }
        }

        private static bool getLayergroupsByName()
        {
            try
            {
                GetGroupLayerDto dto = new GetGroupLayerDto();
                dto.baseUrl = @"https://urbamidplus-collaudo.comune.messina.it/geoserver";
                dto.nomeNodo = "EditingLayer_tematismo";
                dto.password = "geoserver";
                dto.username = "admin";
                dto.workspace = "urbamid";
               

                Console.WriteLine("Inizio Lettura LayerGroup...");
                RestApiGeoServer rest = new RestApiGeoServer();
                LayerDTO response = rest.getLayergroupsByName(dto);

                Console.WriteLine("Fine Lettura LayerGroup...");

                return true;

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return false;
            }
        }

        private static bool uploadStyleToWorkspace()
        {
            try
            {
                GeoServerDto dto = new GeoServerDto();
               // dto.baseUrl = @"http://urbamidplus-collaudo.comune.messina.it/geoserver";
                dto.baseUrl = @"http://192.168.11.222:9191/geoserver";
                dto.baseUrl = @"http://192.168.131.143:9090/geoserver";

                dto.password = "geoserver";
                dto.username = "admin";
                dto.workspace = "urbamid";
                dto.layerName = "u_geo_apf_2007";
                dto.layerName = "APF_2007";

                Console.WriteLine("Inizio Lettura LayerGroup...");
                RestApiGeoServer rest = new RestApiGeoServer();

               bool response =  rest.createStyleInWorkspace(dto);

               response = rest.uploadStyleToWorkspace(dto, "APF_2007");
              

                return response;

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return false;
            }
        }

        private static string GetMimeType(string fileName)
        {
            string mimeType = "application/unknown";
            string ext = System.IO.Path.GetExtension(fileName).ToLower();
            Microsoft.Win32.RegistryKey regKey = Microsoft.Win32.Registry.ClassesRoot.OpenSubKey(ext);
            if (regKey != null && regKey.GetValue("Content Type") != null)
                mimeType = regKey.GetValue("Content Type").ToString();
            return mimeType;
        }

        private static byte[] readLocalShapeFile()
        {
            byte[] buffer;
            string filePath = @"C:\temp\APF_2007.zip";


            string mime = GetMimeType(filePath);

            using (FileStream fs = new FileStream(filePath, FileMode.Open, FileAccess.Read))
            {
                // Create a byte array of file stream length
                byte[] bytes = System.IO.File.ReadAllBytes(filePath);
                //Read block of bytes from stream into the byte array
                fs.Read(bytes, 0, System.Convert.ToInt32(fs.Length));
                //Close the File Stream
                fs.Close();
                return bytes; //return the byte data
            }


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
                //scriviLog(ex.StackTrace);
                return null;

            }
            finally
            {
                fStream.Close();
            }

            return buffer;
        }


        private static bool uploadTIFFonGeoServer()
        {
            bool esito = false;
            RestApiGeoServer rest = new RestApiGeoServer();
            try
            {
                string urlFile = @"C:\\temp\\5880722_orto.tif";

                UploadRasterDto inDto = new UploadRasterDto();
                inDto.baseUrl = @"http://192.168.11.222:9191/geoserver";
                inDto.nome = "5880722_orto.tif";
                inDto.password = "geoserver";
                inDto.titolo = "5880722_orto";
                inDto.username = "admin";
                inDto.workspace = "urbamid";
                inDto.extension = ".worldimage";


                using (FileStream fs = new FileStream(urlFile, FileMode.Open, FileAccess.Read))
                {
                    // Create a byte array of file stream length
                    byte[] bytes = System.IO.File.ReadAllBytes(urlFile);
                    //Read block of bytes from stream into the byte array
                    fs.Read(bytes, 0, System.Convert.ToInt32(fs.Length));
                    //Close the File Stream
                    fs.Close();
                    inDto.arrFile = bytes;

                }

                esito = rest.UploadRaster(inDto);

                 return esito;
            }
            catch (Exception ex)
            {
                return esito;
            }

        }

        private static void dirsearch(string dir)
        {
            try
            {
                NetworkCredential theNetworkCredential = new NetworkCredential("smbuser", "smbpassword");

                using (new NetworkConnection(dir, theNetworkCredential))
                {
                    Directory.CreateDirectory(Path.Combine(dir, "PippoBaudo"));
                    string[] theFolders1 = Directory.GetDirectories(dir);
                }

            }
            catch (System.Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }


        
    }

    public class NetworkConnection : IDisposable
    {
        string _networkName;

        public NetworkConnection(string networkName,
            NetworkCredential credentials)
        {
            _networkName = networkName;

            var netResource = new NetResource()
            {
                Scope = ResourceScope.GlobalNetwork,
                ResourceType = ResourceType.Disk,
                DisplayType = ResourceDisplaytype.Share,
                RemoteName = networkName
            };

            var userName = string.IsNullOrEmpty(credentials.Domain)
                ? credentials.UserName
                : string.Format(@"{0}\{1}", credentials.Domain, credentials.UserName);

            var result = WNetAddConnection2(
                netResource,
                credentials.Password,
                userName,
                0);

            if (result != 0)
            {
                throw new Win32Exception(result);
            }
        }

        ~NetworkConnection()
        {
            Dispose(false);
        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        protected virtual void Dispose(bool disposing)
        {
            WNetCancelConnection2(_networkName, 0, true);
        }

        [DllImport("mpr.dll")]
        private static extern int WNetAddConnection2(NetResource netResource,
            string password, string username, int flags);

        [DllImport("mpr.dll")]
        private static extern int WNetCancelConnection2(string name, int flags,
            bool force);
    }

    [StructLayout(LayoutKind.Sequential)]
    public class NetResource
    {
        public ResourceScope Scope;
        public ResourceType ResourceType;
        public ResourceDisplaytype DisplayType;
        public int Usage;
        public string LocalName;
        public string RemoteName;
        public string Comment;
        public string Provider;
    }

    public enum ResourceScope : int
    {
        Connected = 1,
        GlobalNetwork,
        Remembered,
        Recent,
        Context
    };

    public enum ResourceType : int
    {
        Any = 0,
        Disk = 1,
        Print = 2,
        Reserved = 8,
    }

    public enum ResourceDisplaytype : int
    {
        Generic = 0x0,
        Domain = 0x01,
        Server = 0x02,
        Share = 0x03,
        File = 0x04,
        Group = 0x05,
        Network = 0x06,
        Root = 0x07,
        Shareadmin = 0x08,
        Directory = 0x09,
        Tree = 0x0a,
        Ndscontainer = 0x0b
    }
}
