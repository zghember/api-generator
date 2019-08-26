package generator.reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import generator.element.ObjType;
import generator.element.Var;
import generator.element.VarClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonReader {
  private JsonFactory factory = new JsonFactory();

  public VarClass genJavaBean(String obj, String name) {
    try {
      JsonParser jp = factory.createParser(obj); // or URL, Stream, Reader, String, byte[]
      Var thisVar = new Var();
      thisVar.setName(name);
      return genValue(jp, thisVar).getVarClass();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);

    }
  }

  public Var genField(JsonParser jp) throws IOException {
    if (jp.getCurrentToken() != JsonToken.FIELD_NAME) {
      throw new IllegalArgumentException("illegal json format");
    }
    log.info("start to generate field " + jp.getCurrentName());
    Var thisVar = new Var();
    thisVar.setName(jp.getCurrentName());
    thisVar = genValue(jp, thisVar);
    log.info("end generate field " + thisVar.getName());
    return thisVar;
  }

  public Var genValue(JsonParser jp, Var thisVar) throws IOException {
    log.info("start to generate value for field " + thisVar.getName());

    switch (jp.nextToken()) {
      case START_OBJECT:
        log.info("the value for this field is object");
        thisVar.setType(ObjType.MAP);
        thisVar.setVarClass(genClass(jp, thisVar.getName()));
        break;
      case VALUE_NULL:
        log.info("the value for this field is null");
        thisVar.setType(ObjType.NULL);
        break;
      case START_ARRAY:
        log.info("the value for this field is array");
        thisVar.setIsArray(true);
        genValue(jp, thisVar);
        if (thisVar.getType() == ObjType.NULL) {
          break;
        }
        while (jp.nextToken() != JsonToken.END_ARRAY) {
          //
        }
        break;
      case END_ARRAY:
        // empty array
        log.info("the value for this field is end array");
        thisVar.setType(ObjType.NULL);
        break;
      case VALUE_TRUE:
      case VALUE_FALSE:
        log.info("the value for this field is boolean");
        thisVar.setType(ObjType.BOOLEAN);
        break;
      case VALUE_STRING:
        log.info("the value for this field is string");
        thisVar.setType(ObjType.STRING);
        break;
      case VALUE_NUMBER_INT:
        log.info("the value for this field is long");
        thisVar.setType(ObjType.LONG);
        break;
      case VALUE_NUMBER_FLOAT:
        log.info("the value for this field is double");
        thisVar.setType(ObjType.DOUBLE);
        break;
      case VALUE_EMBEDDED_OBJECT:
        log.info("the value for this field is embedded object");
      case END_OBJECT:
        log.info("the value for this field is end object");
      case FIELD_NAME:
        log.info("the value for this field is field name");
      case NOT_AVAILABLE:
        log.info("the value for this field is not available");
      default:
        throw new IllegalStateException();
    }
    log.info("end generate value for field " + thisVar.getName());
    return thisVar;
  }


  public VarClass genClass(JsonParser jp, String className) throws IOException {
    log.info("start to generate class " + className);
    VarClass thisClass = new VarClass();
    thisClass.setName(className);

    if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
      throw new IllegalArgumentException("illegal json format");
    }

    while (jp.nextToken() == JsonToken.FIELD_NAME) {
      Var var = genField(jp);
      thisClass.addVar(var);
    }

//    if (jp.nextToken() != null) {
//      System.out.println(jp.getCurrentLocation());
//      System.out.println(jp.getCurrentToken());
//      System.out.println(jp.getCurrentName());
//      System.out.println(jp.getCurrentValue());
//    }
    if (jp.getCurrentToken() == JsonToken.END_OBJECT) {
      log.info("end generate class " + className);
      return thisClass;
    } else {
      throw new IllegalArgumentException("illegal json format");
    }
  }
}
