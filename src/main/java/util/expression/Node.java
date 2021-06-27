package util.expression;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Tibor Racman
 * ADVANCED PROGRAMMING PROJECT 2020/21 - An abstract class modeling the concept of a node in the computation tree
 */

public abstract class Node {
  private final List<Node> children;

  public Node(List<Node> children) {
    this.children = children;
  }

  public List<Node> getChildren() {
    return children;
  }

  public abstract double calculate(Map<String, Double> tuple) throws IllegalArgumentException;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Node node = (Node) o;
    return Objects.equals(children, node.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(children);
  }

}


