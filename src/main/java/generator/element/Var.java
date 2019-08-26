package generator.element;

import lombok.Data;

@Data
public class Var {
  String name;
  ObjType type;
  VarClass varClass;
  Boolean isArray = false;
}
