package generator.element;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VarClass {
  List<Import> imports = new ArrayList<>();
  String name;
  List<Var> vars = new ArrayList<>();
  List<Method> methods = new ArrayList<>();

  public VarClass addVar(Var var) {
    this.vars.add(var);
    return this;
  }
}
