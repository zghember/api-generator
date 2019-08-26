package generator.element;

import lombok.Data;

import java.util.List;

@Data
public class Package {
  String name;
  List<Var> vars;
  List<Package> packages;
}
