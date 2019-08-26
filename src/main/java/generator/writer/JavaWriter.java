package generator.writer;

import generator.element.Api;
import generator.element.Method;
import generator.element.Var;
import generator.element.VarClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JavaWriter {
  private final String packageName;
  private String template;

  public JavaWriter(String packageName) {
    this.packageName = packageName;
    try {
      InputStream stream = this.getClass().getResourceAsStream("/templates/template.java");
      template = IOUtils.toString(stream, Charset.defaultCharset());
    } catch (IOException io) {
      // do nothing
    }
  }

  public void writeCode(Api api) {

  }

  public Map<String, VarClass> findAllClass(VarClass clz) {
    Map<String, VarClass> classMap = new HashMap<>();
    if (clz == null) {
      return new HashMap<>();
    }
    classMap.put(handleName(clz.getName()), clz);
    for (Var var: clz.getVars()) {
      classMap.putAll(findAllClass(var.getVarClass()));
    }
    return classMap;
  }

  public void writeAllClass(Map<String, VarClass> classMap) {
    for(VarClass clz : classMap.values()) {
      writeClass(clz);
    }
  }

  public void writeClass(VarClass clz) {
      String clzStr = template.replace("{packageName}", packageName)
      .replace("{className}", handleName(clz.getName()))
          .replace("{time}", new DateTime().toString())
          .replace("{javaVars}", handleVars(clz.getVars()))
          .replace("{javaMethods}", handleMethods(clz.getMethods()));
      System.out.println(clzStr);
  }

  public String handleName(String name) {
    name = name.toLowerCase();
    return WordUtils.capitalizeFully(name);
  }

  public String handleVars(List<Var> vars) {
    StringBuilder init = new StringBuilder();
    for (Var var:vars) {
      init.append(handleVar(var));
    }
    return init.toString();
  }

  public String handleVar(Var var) {
    String format = "    %s %s;\n";
    if (var.getIsArray()) {
      format = "    List<%s> %s;\n";
    }

    switch (var.getType()) {
      case MAP:
        return String.format(format, handleName(var.getVarClass().getName()), var.getName());
      case LONG:
        return String.format(format, "Long", var.getName());
      case NULL:
        return "";
      case DOUBLE:
        return String.format(format, "Double", var.getName());
      case STRING:
        return String.format(format, "String", var.getName());
      case BOOLEAN:
        return String.format(format, "Boolean", var.getName());
    }
    throw new IllegalStateException();
  }

  public String handleMethods(List<Method> methods) {
    return "";
  }
}
