import generator.element.VarClass;
import generator.reader.JsonReader;
import generator.writer.JavaWriter;
import org.junit.jupiter.api.Test;

public class JsonTest {
  JsonReader reader = new JsonReader();
  JavaWriter writer = new JavaWriter("com.pkuember.generated");
  String p = "{\"status\":\"OK\",\"message\":\"\",\"body\":{\"paragraphs\":[{\"text\":\"%sql \\nselect age, count(1) value\\nfrom bank \\nwhere age < 30 \\ngroup by age \\norder by age\",\"config\":{\"colWidth\":4,\"graph\":{\"mode\":\"multiBarChart\",\"height\":300,\"optionOpen\":false,\"keys\":[{\"name\":\"age\",\"index\":0,\"aggr\":\"sum\"}],\"values\":[{\"name\":\"value\",\"index\":1,\"aggr\":\"sum\"}],\"groups\":[],\"scatter\":{\"xAxis\":{\"name\":\"age\",\"index\":0,\"aggr\":\"sum\"},\"yAxis\":{\"name\":\"value\",\"index\":1,\"aggr\":\"sum\"}}}},\"settings\":{\"params\":{},\"forms\":{}},\"jobName\":\"paragraph_1423500782552_-1439281894\",\"id\":\"20150210-015302_1492795503\",\"results\":{\"code\":\"SUCCESS\",\"msg\":[{\"type\":\"TABLE\",\"data\":\"age\\tvalue\\n19\\t4\\n20\\t3\\n21\\t7\\n22\\t9\\n23\\t20\\n24\\t24\\n25\\t44\\n26\\t77\\n27\\t94\\n28\\t103\\n29\\t97\\n\"}]},\"dateCreated\":\"Feb 10, 2015 1:53:02 AM\",\"dateStarted\":\"Jul 3, 2015 1:43:17 PM\",\"dateFinished\":\"Jul 3, 2015 1:43:23 PM\",\"status\":\"FINISHED\",\"progressUpdateIntervalMs\":500}],\"name\":\"Zeppelin Tutorial\",\"id\":\"2A94M5J1Z\",\"angularObjects\":{},\"config\":{\"looknfeel\":\"default\"},\"info\":{}}}";

  @Test
  public void testJson() {
    String json = "{\"dog\":[{\"name\":\"Rufus\",\"breed\":\"labrador\",\"count\":1,\"twoFeet\":false},{\"name\":\"Marty\",\"breed\":\"whippet\",\"count\":1,\"twoFeet\":false}],\"cat\":{\"name\":\"Matilda\"}}";
    VarClass clz = reader.genJavaBean(json, "request");
    System.out.println(clz);
  }

  @Test
  public void testJavaWriter() {
//    String json = "{\"dog\":[{\"name\":\"Rufus\",\"breed\":\"labrador\",\"count\":1,\"twoFeet\":false},{\"name\":\"Marty\",\"breed\":\"whippet\",\"count\":1,\"twoFeet\":false}],\"cat\":{\"name\":\"Matilda\"}}";
    VarClass clz;
//    clz = reader.genJavaBean(json, "request");
//    writer.writeAllClass(writer.findAllClass(clz));
//    writer.writeClass(clz);
    clz = reader.genJavaBean(p, "request");
    writer.writeAllClass(writer.findAllClass(clz));
  }
}
