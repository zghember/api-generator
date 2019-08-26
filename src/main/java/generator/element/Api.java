package generator.element;

import lombok.Data;

@Data
public class Api {
  String uri;
  Var req;
  Var res;
}
