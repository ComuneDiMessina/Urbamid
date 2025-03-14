using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace UrbamidAddIn.Dto
{
    public class Workspace
    {

        [JsonProperty("name")]
        public string name { get; set; }
    }

    public class Published
    {

        [JsonProperty("@type")]
        public string tipoPublished { get; set; }

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("href")]
        public string href { get; set; }
    }

    public class Publishables
    {

        [JsonProperty("published")]
        [JsonConverter(typeof(SingleOrArrayConverter<Published>))]
        public IList<Published> published { get; set; }
        //public Published published { get; set; }
    }

    public class SingleOrArrayConverter<T> : JsonConverter
    {
        public override bool CanConvert(Type objectType)
        {
            return (objectType == typeof(List<T>));
        }

        public override object ReadJson(JsonReader reader, Type objectType, object existingValue, JsonSerializer serializer)
        {
            JToken token = JToken.Load(reader);
            if (token.Type == JTokenType.Array)
            {
                return token.ToObject<List<T>>();
            }
            return new List<T> { token.ToObject<T>() };
        }

        public override bool CanWrite
        {
            get { return false; }
        }

        public override void WriteJson(JsonWriter writer, object value, JsonSerializer serializer)
        {
            throw new NotImplementedException();
        }
    }

    public class Style
    {
       

        [JsonProperty("name", NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public string name { get; set; }
        [JsonProperty("href", NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public string href { get; set; }
    }

    public class Styles
    {

        [JsonProperty("style")]
        public IList<Style> style { get; set; }
    }

    //public IList<Style> style { get; set; } 

    public class Bounds
    {

        [JsonProperty("minx")]
        public double minx { get; set; }

        [JsonProperty("maxx")]
        public double maxx { get; set; }

        [JsonProperty("miny")]
        public double miny { get; set; }

        [JsonProperty("maxy")]
        public double maxy { get; set; }

        [JsonProperty("crs")]
        public string crs { get; set; }
    }

    public class Keywords
    {

        [JsonProperty("string")]
        public IList<string> chiave { get; set; }
    }

    public class LayerGroup
    {

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("mode")]
        public string mode { get; set; }

        [JsonProperty("title")]
        public string title { get; set; }

        [JsonProperty("abstractTxt")]
        public string abstractTxt { get; set; }

        [JsonProperty("workspace")]
        public Workspace workspace { get; set; }

        [JsonProperty("publishables")]
        public Publishables publishables { get; set; }

        [JsonProperty("styles")]
        public Styles styles { get; set; }

        [JsonProperty("bounds")]
        public Bounds bounds { get; set; }

        [JsonProperty("keywords")]
        public Keywords keywords { get; set; }

        [JsonProperty("href")]
        public string href { get; set; }
    }

    public class LayerDTO : BaseDto
    {

        [JsonProperty("layerGroup")]
        public LayerGroup layerGroup { get; set; }
    }

    public class LayerAreaTematicaDTO : BaseDto
    {

        [JsonProperty("layerGroup")]
        public LayerGroup layerGroup { get; set; }
    }
}

