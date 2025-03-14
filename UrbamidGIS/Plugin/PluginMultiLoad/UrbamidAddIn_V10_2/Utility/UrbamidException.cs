using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Utility
{
    public class UrbamidException : Exception
    {

        public TipoErroreEnum TipoErrore { get; set; }
        public UrbamidException(TipoErroreEnum tipoErrore)
        {
            TipoErrore = tipoErrore;
        }

        public static string GetMessaggio(TipoErroreEnum tipoErrore)
        {
            switch (tipoErrore)
            {
                case TipoErroreEnum.DettaglioGruppo:
                    return "Errore recupero Gruppo";
                case TipoErroreEnum.DeleteLayer:
                    return "Errore cancellazione layer";
                case TipoErroreEnum.UploadRaster:
                    return "Errore import Raster";
                case TipoErroreEnum.ListaGruppi:
                    return "Errore recupero gruppi di layer da GeoServer";
                case TipoErroreEnum.UploadShape:
                    return "Errore Upload Shape su GeoServer";
                case TipoErroreEnum.AddLayerToGroup:
                    return "Errore Aggiunta Layer a Gruppo";
                case TipoErroreEnum.DettaglioLayer:
                    return "Errore nel recupero informazioni Layer";
                case TipoErroreEnum.AddStyleToWorkSpace:
                    return "Errore aggiunta stile a Workspace";
                case TipoErroreEnum.UploadStyleToWorkSpace:
                    return "Errore upload stile a Workspace";
                case TipoErroreEnum.LayersInWorkSpace:
                    return "Errore recupero lista Layers da Workspace";
                case TipoErroreEnum.GroupsInWorkSpace:
                    return "Errore recupero Gruppi da Workspace";
                case TipoErroreEnum.StyleToLayer:
                    return "Errore associazione Stile a Layer";
                case TipoErroreEnum.StyleNonGestito:
                    return "Errore, la simbologia presente nello stile non è convertibile in GeoServer";
                case TipoErroreEnum.PublishStyle:
                    return "Errore in fase di pubblicazione stile";
                case TipoErroreEnum.ConvertShapeToDB:
                    return "Errore in importazione Layer in Database";
                case TipoErroreEnum.PublishTableLayer:
                    return "Errore in fase di pubblicazione Tabella Layer";
                case TipoErroreEnum.FeatureRaster:
                    return "Errore nel recupero delle Feature del Raster";
                case TipoErroreEnum.FeatureLayer:
                    return "Errore nel recupero delle Feature del Layer";
                case TipoErroreEnum.UpdateLayer:
                    return "Errore modifica Layer";
                case TipoErroreEnum.UpdateLayerWMS:
                    return "Errore modifica Layer WMS";
                case TipoErroreEnum.UpdateGroup:
                    return "Errore modifica Gruppo";
                case TipoErroreEnum.DeleteDataStore:
                    return "Errore cancellazione Store da GeoServer";
                case TipoErroreEnum.DeleteRaster:
                    return "Errore cancellazione Raster da GeoServer";
                case TipoErroreEnum.DeleteStyle:
                    return "Errore cancellazione Stile da GeoServer";
                case TipoErroreEnum.DeleteGroup:
                    return "Errore cancellazione Gruppo da GeoServer";
                case TipoErroreEnum.GetStyleFromWorkSpace:
                    return "Errore recupero Stili da Workspace";
                case TipoErroreEnum.CreateLayerGroup:
                    return "Errore creazione Gruppo su GeoServer";
                case TipoErroreEnum.ReadShapeFile:
                    return "Errore lettura Shape File da ArcMap";
                case TipoErroreEnum.UploadShapeToDB:
                    return "Errore importazione Shape in Database";
                case TipoErroreEnum.UpdateFeatureLayer:
                    return "Errore aggiornamento attributi del Layer";
                case TipoErroreEnum.ListaLayers:
                    return "Errore recupero Layers da GeoServer";
                case TipoErroreEnum.DeleteFeatureLayer:
                    return "Errore cancellazione attributi del Layer";
                case TipoErroreEnum.CheckWMSSTore:
                    return "Errore nel recupero degli Store WMS";
                case TipoErroreEnum.AddWMSSTore:
                    return "Errore nel salvataggio del WMS";
                case TipoErroreEnum.LayersFromWMS:
                    return "Errore recupero Layers da WMS";
                case TipoErroreEnum.ADDWMSLayer:
                    return "Errore nel salvataggio del Layer WMS";
                case TipoErroreEnum.GetStoreWMS:
                    return "Errore nel recupero degli stores WMS";
                case TipoErroreEnum.DeleteStoreWMS:
                    return "Errore nella cancellazione dello store WMS";
                case TipoErroreEnum.DeleteLayerWMS:
                    return "Errore nella cancellazione Layer WMS";
                default:
                    return "Errore Generico";
            }
        }
    }

    public enum TipoErroreEnum
    {
        DettaglioGruppo,
        DettaglioLayer,
        DeleteLayer,
        DeleteLayerWMS,
        DeleteDataStore,
        DeleteRaster,
        DeleteStyle,
        DeleteGroup,
        ListaGruppi,
        AddLayerToGroup,
        AddStyleToWorkSpace,
        LayersInWorkSpace,
        GroupsInWorkSpace,
        StyleToLayer,
        StyleNonGestito,
        PublishStyle,
        ConvertShapeToDB,
        PublishTableLayer,
        FeatureRaster, 
        FeatureLayer,
        UpdateLayer,
        UpdateLayerWMS,
        UpdateGroup,
        UploadShape,
        UploadRaster,
        UploadStyleToWorkSpace,
        GetStyleFromWorkSpace,
        CreateLayerGroup,
        ReadShapeFile,
        UploadShapeToDB,
        UpdateFeatureLayer,
        ListaLayers,
        DeleteFeatureLayer,
        CheckWMSSTore,
        AddWMSSTore, 
        LayersFromWMS,
        ADDWMSLayer,
        GetStoreWMS,
        DeleteStoreWMS
    }
}