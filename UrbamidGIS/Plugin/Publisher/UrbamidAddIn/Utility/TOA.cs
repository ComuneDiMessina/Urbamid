using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using UrbamidAddIn.Dto;

namespace UrbamidAddIn
{
   public class TOA
    {

        public static GetListaLayerInDto assemblaGetListaLayerInDto(string server, string pass, string user, string workspace)
        {
            GetListaLayerInDto output = new Dto.GetListaLayerInDto();
            output.baseUrl = server;
            output.password = pass;
            output.username = user;
            output.workspace = workspace;

            return output;

        }

        internal static GenericLayer assemblaGenericLayer(Layer input)
        {
            GenericLayer layer = new Dto.GenericLayer();
            layer.nome = input.name;
            layer.isLayerGroup = false;
            layer.linkDetail = input.link;
            return layer;

        }

        public static GetFeatureTypeDto assemblaFeatureTypeDto(GetListaLayerInDto input)
        {
            GetFeatureTypeDto output = new Dto.GetFeatureTypeDto();
            output.baseUrl = input.baseUrl;
            output.password = input.password;
            output.username = input.username;
            output.workspace = input.workspace;
        
            return output;
        }

        internal static GenericLayer assemblaGenericLayer(LayerGroup input)
        {
            GenericLayer layer = new Dto.GenericLayer();
            layer.isLayerGroup = true;
            layer.linkFeature = String.Empty;
            layer.nome = input.name;
            layer.titolo = input.title;
            return layer;
        }

        public static GeoServerDto assemblaGeoServerDTO(GetListaLayerInDto input)
        {
            GeoServerDto output = new Dto.GeoServerDto();
            output.username = input.username;
            output.password = input.password;
            output.baseUrl = input.baseUrl;
            output.workspace = input.workspace;

            return output;
        }

        internal static GetGroupLayerDto assemblaGetDettaglioGruppo(GetListaLayerInDto input)
        {
            GetGroupLayerDto output = new Dto.GetGroupLayerDto();
            output.password = input.password;
            output.username = input.username;
            output.baseUrl = input.baseUrl;
            output.workspace = input.workspace;

            return output;
        }

        internal static CreateLayerGroupInDto assemblaLayerGroup(GetListaLayerInDto inLista, LayerGroup layerGroup)
        {
            CreateLayerGroupInDto output = new Dto.CreateLayerGroupInDto();
            output.password = inLista.password;
            output.username = inLista.username;
            output.workspace = inLista.workspace;
            output.baseUrl = inLista.baseUrl;
            output.abstractTxt = layerGroup.title;
            output.mode =layerGroup.mode;
            output.name = layerGroup.name;
            output.title = layerGroup.title;
            output.bounds = layerGroup.bounds;
            output.workspaces = layerGroup.workspace;
            output.keywords = layerGroup.keywords;
            output.publishables = new Publishables();
            output.publishables.published = new List<Published>();
            output.styles = new Styles();
            output.styles.style = new List<Style>();

            return output;


        }

        internal static CreateLayerGroupInDto createLayerGroupInDto(BaseDto dtoBase, LayerGroup input)
        {
            CreateLayerGroupInDto outDTO = new Dto.CreateLayerGroupInDto();
            outDTO.baseUrl = dtoBase.baseUrl;
            outDTO.password = dtoBase.password;
            outDTO.username = dtoBase.username;
            outDTO.workspace = dtoBase.workspace;
            outDTO.abstractTxt = input.abstractTxt;
            outDTO.bounds = input.bounds;
            outDTO.keywords = input.keywords;
            outDTO.mode = input.mode;
            outDTO.name = input.name;
            outDTO.publishables = input.publishables;
            outDTO.styles = input.styles;
            outDTO.title = input.title;
            outDTO.workspaces = input.workspace;

            if (input.publishables != null)
                outDTO.publishables = assemblaPublishables(input.publishables);

            return outDTO;
        }

        internal static Publishables assemblaPublishables(Publishables input)
        {
            Publishables output = new Dto.Publishables();
            output.published = assemblaPublished(input.published);
            return output;
        }

        internal static List<Published> assemblaPublished(IList<Published> input)
        {
            List<Published> output = new List<Dto.Published>();
            if (input != null)
            {
                foreach (Published pubb in input)
                {
                    output.Add(pubb);

                }
            }
            return output;
        }

        internal static Published assemblaPublished(Published input)
        {
            Published output = new Dto.Published();
            output.href = input.href;
            output.name = input.name;
            output.tipoPublished = input.tipoPublished;

            return output;
        }

        internal static DeleteDatastoreDto assemblaDeleteDataStore(GetListaLayerInDto input)
        {
            DeleteDatastoreDto output = new Dto.DeleteDatastoreDto();
            output.baseUrl = input.baseUrl;
            output.password = input.password;
            output.username = input.username;
            output.workspace = input.workspace;

            return output;
        }

        internal static EditLayerDto assemblaEditLayer(GetListaLayerInDto input)
        {
            EditLayerDto output = new EditLayerDto();
            output.baseUrl = input.baseUrl;
            output.password = input.password;
            output.username = input.username;
            output.workspace = input.workspace;


            return output;
        }


        internal static EditLayerDto assemblaEditLayer(BaseDto input)
        {
            EditLayerDto output = new EditLayerDto();
            output.baseUrl = input.baseUrl;
            output.password = input.password;
            output.username = input.username;
            output.workspace = input.workspace;
            return output;
        }

        internal static BaseDto assemblaBase(string server, string userName, string password, string defaultWorkSpace)
        {
            BaseDto outDto = new Dto.BaseDto();
            outDto.baseUrl = server;
            outDto.password = password;
            outDto.username = userName;
            outDto.workspace = defaultWorkSpace;


            return outDto;
        }

        internal static UploadRasterDto assmblaUploadRaster(BaseDto dtoBase, byte[] arrByte, string nomeRaster)
        {
            UploadRasterDto outDto = new Dto.UploadRasterDto();
            outDto.arrFile = arrByte;
            outDto.baseUrl = dtoBase.baseUrl;
            outDto.nome = nomeRaster;
            outDto.password = dtoBase.password;
            outDto.titolo = nomeRaster;
            outDto.username = dtoBase.username;
            outDto.workspace = dtoBase.workspace;

            return outDto;
        }

        public static GeoServerDto assemblaGeoServerDTO(BaseDto inDto, String link)
        {
            GeoServerDto outDto = new Dto.GeoServerDto();
            outDto.baseUrl = inDto.baseUrl;
            outDto.password = inDto.password;
            outDto.username = inDto.username;
            outDto.workspace = inDto.workspace;
            outDto.linkDettaglio = link;



            return outDto;
        }

        internal static GetGroupLayerDto assemblaGetDettaglioGruppo(BaseDto dtoBase, string name)
        {
            GetGroupLayerDto outDto = new Dto.GetGroupLayerDto();
            outDto.baseUrl = dtoBase.baseUrl;
            outDto.link = "";
            outDto.nomeNodo = name;
            outDto.password = dtoBase.password;
            outDto.username = dtoBase.username;
            outDto.workspace = dtoBase.workspace;
            return outDto;

        }

        public static GetFeatureTypeDto assemblaFeatureTypeDto(BaseDto dtoBase, string href)
        {
            GetFeatureTypeDto outDto = new Dto.GetFeatureTypeDto();
            outDto.baseUrl = dtoBase.baseUrl;
            outDto.link = href;
            outDto.password = dtoBase.password;
            outDto.username = dtoBase.username;
            outDto.workspace = dtoBase.workspace;

            return outDto;
        }

        internal static GetGroupLayerDto assemblaGroupLayer(BaseDto dtoBase, string nomeNodo)
        {
            GetGroupLayerDto outDto = new GetGroupLayerDto();
            outDto.baseUrl = dtoBase.baseUrl;
            outDto.nomeNodo = nomeNodo;
            outDto.password = dtoBase.password;
            outDto.username = dtoBase.username;
            outDto.workspace = dtoBase.workspace;

            return outDto;
        }

        internal static GetListaLayerInDto assemblaGetListaLayerInDto(BaseDto dtoBase)
        {
            GetListaLayerInDto outDto = new GetListaLayerInDto();
            outDto.baseUrl = dtoBase.baseUrl;
            outDto.password = dtoBase.password;
            outDto.username = dtoBase.username;
            outDto.workspace = dtoBase.workspace;


            return outDto;
        }

        internal static GetGroupLayerDto assemblaGetDettaglioGruppo(BaseDto dtoBase, LayerGroup item)
        {
            GetGroupLayerDto outDto = new Dto.GetGroupLayerDto();

            outDto.link = item.href;
            outDto.nomeNodo = item.name;
            outDto.baseUrl = dtoBase.baseUrl;
            outDto.password = dtoBase.password;
            outDto.username = dtoBase.username;
            outDto.workspace = dtoBase.workspace;

            return outDto;
        }

        internal static DeleteDatastoreDto assemblaDeleteDataStore(BaseDto dtoBase, string nomeNodo)
        {
            DeleteDatastoreDto outDto = new Dto.DeleteDatastoreDto();
            outDto.baseUrl = dtoBase.baseUrl;
            outDto.nomeDataStore = nomeNodo;
            outDto.password = dtoBase.password;
            outDto.username = dtoBase.username;
            outDto.workspace = dtoBase.workspace;

            return outDto;
        }
    }
}
