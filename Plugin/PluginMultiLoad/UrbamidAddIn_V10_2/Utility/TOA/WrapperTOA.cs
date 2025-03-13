using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using UrbamidAddIn_V10_2.Dto;

namespace UrbamidAddIn_V10_2.Utility
{
    public class WrapperTOA
    {
        #region Service To Dto

         public static DeleteFeature_OutDto assembler(DeleteFeature_Service service)
        {
            DeleteFeature_OutDto output = new DeleteFeature_OutDto();
            output.IsSuccess = service.success;
            output.Message = service.sEcho;

            return output;
        }
       
      


        internal static EditFeature_OutDto assembler(EditFeature_Service service)
        {
            EditFeature_OutDto output = new EditFeature_OutDto();
            output.IsSuccess = service.success;
            output.Message = service.sEcho;

            return output;
        }

        internal static GetDettaglioGruppo_OutDto assembler(GetDettaglioGruppo_Service service)
        {
            GetDettaglioGruppo_OutDto output = new GetDettaglioGruppo_OutDto();
            output.IsSuccess = service.success;
            output.Message = service.sEcho;

            if(service.dettaglio != null)
                output.Gruppo = service.dettaglio.gruppo;

            return output;

        }

        public static GetListaLayerGroups_OutDto assembler(GetListaLayerGroups_Service service)
        {
            GetListaLayerGroups_OutDto output = new GetListaLayerGroups_OutDto();
            output.IsSuccess = service.success;
            output.Message = service.sEcho;

            if (service.dettaglio != null && service.dettaglio.listaGruppi != null)
                output.gruppi = service.dettaglio.listaGruppi;



            return output;


        }

      


        public static GetDettaglioLayer_OutDto assembler(GetLayer_Service input)
        {
            GetDettaglioLayer_OutDto output = new GetDettaglioLayer_OutDto();
            output.IsSuccess = true;
            output.Message = input.sEcho;
            output.Exist = input.success; // indica se il layer è già presente
            if (input.dettaglio != null)
                output.layer = input.dettaglio.layer;


            return output;
        }

        //private static Layer assembler(LayerDetail input)
        //{
        //    Layer output = new Layer();
        //    if(input != null && input.layer != null)
        //    {
        //        output = input.layer;
            
        //    }

        //    return output;
        //}

        public static UploadShape_OutDto assembler(UploadShapeDb_Service service)
        {
            UploadShape_OutDto output = new UploadShape_OutDto();
            output.IsSuccess = service.success;
            if (service.sEcho != null)
            {
                output.Nome = service.sEcho.ToString();
                output.Message = service.sEcho.ToString();
            }
            return output;
        }

        public static ConvertShapeToDb_OutDto assembler(ConvertToLayer_Service service)
        {
            ConvertShapeToDb_OutDto output = new ConvertShapeToDb_OutDto();
            output.IsSuccess = service.success;
            output.Message = service.sEcho;
            return output;
        }

        public static AddLayerToGroup_OutDto assembler(AddLayerToGroup_Service service)
        {
            AddLayerToGroup_OutDto output = new AddLayerToGroup_OutDto();
            output.IsSuccess = service.success;
            output.Message = service.sEcho;
            return output;
        }

        public static GetFeatureByLayer_OutDto assembler(GetFeatureTypeLayer_Service service)
        {
            GetFeatureByLayer_OutDto output = new GetFeatureByLayer_OutDto();
            output.IsSuccess = service.success;
            output.Message = service.sEcho;
            if (service.dettaglio != null)
                output.feature = service.dettaglio.featureType;


            return output;
        }

        public static DeleteLayer_OutDto assembler(DeleteLayer_Service service)
        {
            DeleteLayer_OutDto output = new DeleteLayer_OutDto();
            output.IsSuccess = service.success;
            output.Message = service.sEcho;
            return output;
        }

        public static DeleteStyle_OutDto assembler(DeleteStyle_Service service)
        {
            DeleteStyle_OutDto output = new DeleteStyle_OutDto();
            output.IsSuccess = service.success;
            output.Message = service.sEcho;
            return output;
        }

      

        public static GetListaLayers_OutDto assembler(GetListaLayers_Service service)
        {
            GetListaLayers_OutDto output = new GetListaLayers_OutDto();
            output.IsSuccess = service.success;
            output.Message = service.sEcho;

            output.layers = service.lista;

            return output;
        }


        internal static PublishTable_OutDto assembler(PublishTable_Service service)
        {
            PublishTable_OutDto output = new PublishTable_OutDto();
            output.IsSuccess = service.success;
            output.Message = service.sEcho;

            return output;
        }

        #endregion

        #region Dto To Service

        public static DeleteFeature_InDto assemblerDeleteFeature(string wrapperUrl, string workspace, string nome)
        {
            DeleteFeature_InDto output = new DeleteFeature_InDto();
            output.baseUrl = wrapperUrl;
            output.nomeFeatureType = nome;
            output.workspace = workspace;

            return output;
        }


        public static GetDettaglioGruppo_InDto assemblerDettaglioGruppo(string urlWrapper, string workspace, string nome)
        {
            GetDettaglioGruppo_InDto output = new GetDettaglioGruppo_InDto();
            output.baseUrl = urlWrapper;
            output.nomeGruppo = nome;
            output.workspace = workspace;

            return output;
        }

        public static GetListaLayers_InDto assemblerGetLista(string urlWrapper, string workspace)
        {
            GetListaLayers_InDto output = new GetListaLayers_InDto();
            output.baseUrl = urlWrapper;
            output.workspace = workspace;

            return output;
        }

        public static UploadShape_InDto assembler(String urlWrapper, String pathZip)
        {
            UploadShape_InDto output = new UploadShape_InDto();
            output.baseUrl = urlWrapper;
            output.pathFile = pathZip;
            output.overwrite = true;

            return output;
        }

        public static GetDettaglioLayer_InDto assembler(String urlWrapper,string nomeLayer, string workspace, bool isLayerDb)
        {
            GetDettaglioLayer_InDto output = new GetDettaglioLayer_InDto();
            output.baseUrl = urlWrapper;
            output.nomeLayer = nomeLayer;
            output.workspace = workspace;
            output.isLayerDb = isLayerDb;
            return output;

        }

        public static ConvertShapeToDb_InDto assemblerConvert(string urlWrapper, string nomeLayer)
        {
            ConvertShapeToDb_InDto output = new ConvertShapeToDb_InDto();
            output.baseUrl = urlWrapper;
            output.nomeLayer = nomeLayer;

            return output;
        }

        public static PublishTable_InDto assembler(string urlWrapper, string datastore, string nomeLayer, string titoloLayer,
                                                     bool shapeToDb, bool estrazionePart, bool defaultView, string workspace)
        {
            PublishTable_InDto output = new PublishTable_InDto();
            output.baseUrl = urlWrapper;
            output.dataStore = datastore;
            output.defaultView = defaultView;
            output.estrazionePart = estrazionePart;
            output.nomeLayer = nomeLayer;
            output.shapeToDb = shapeToDb;
            output.titoloLayer = titoloLayer;
            output.workspace = workspace;

            return output;

        }

        public static AddLayerToGroup_InDto assembler(string urlWrapper, string workspace, string nomeNodo, string nomeLayer, string stile)
        {
            AddLayerToGroup_InDto output = new AddLayerToGroup_InDto();
            output.baseUrl = urlWrapper;
            output.nomeGruppo = nomeNodo.Replace("urbamid:", "");
            output.nomeLayer = nomeLayer;
            output.stile = stile;
            output.workspace = workspace;

            return output;
        }

        public static DeleteLayer_InDto assemblerDeleteLayer(string urlWrapper, string workspace, string nomeLayer)
        {
            DeleteLayer_InDto output = new DeleteLayer_InDto();
            output.baseUrl = urlWrapper;
            output.workspace = workspace;
            output.nomeLayer = nomeLayer;
            return output;
        }

        public static DeleteStyle_InDto assemblerDeleteStyle(string urlWrapper, string workspace, string nomeStile)
        {
            DeleteStyle_InDto output = new DeleteStyle_InDto();
            output.baseUrl = urlWrapper;
            output.workspace = workspace;
            output.nomeStile = nomeStile;

            return output;
        }

        public static GetFeatureByLayer_InDto assemblaGetFeature(string urlWrapper, string workspace, string featureType, bool isDabase)
        {
            GetFeatureByLayer_InDto outDto = new GetFeatureByLayer_InDto();
            outDto.baseUrl = urlWrapper;
     
            outDto.featureType = featureType;
            outDto.workspace = workspace;
            //outDto.isFeatureDb = isDabase;

            return outDto;

        }

        public static GetListaLayerGroups_InDto assemblerGetGruppi(string urlWrapper, string workspace)
        {
            GetListaLayerGroups_InDto output = new GetListaLayerGroups_InDto();
            output.baseUrl = urlWrapper;
            output.workspace = workspace;

            return output;
        }

        #endregion


        #region metodi privati



        #endregion



        #region Common
        internal static GenericLayer assembler(LayerGroup input)
        {
            GenericLayer output = new GenericLayer();
            output.isLayerGroup = true;
            output.linkDetail = input.href;
            output.linkFeature = String.Empty;
            output.nome = input.name;
            output.titolo = input.title;

            return output;
        }

        #endregion

        internal static EditFeature_InDto assemblerEditFeature(string wrapperUrl, string nome, string keys, string workspace, string titolo)
        {
            EditFeature_InDto output = new EditFeature_InDto();
            output.baseUrl = wrapperUrl;
            output.nomeFeature = nome;
            output.workspace = workspace;
            output.keywords = keys;
            output.title = titolo;

            return output;
        }





       
    }

}
