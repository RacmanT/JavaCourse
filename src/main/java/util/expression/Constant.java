package util.expression;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Tibor Racman
 * ADVANCED PROGRAMMING PROJECT 2020/21 - A Class modeling a node associated with a constant in a mathematical expression
 */

public class Constant extends Node {

  private final double value;

  public Constant(double value) {
    super(Collections.emptyList());
    this.value = value;
  }

  private double getValue() {
    return value;
  }

  @Override
  public double calculate(Map<String, Double> tuple) throws IllegalArgumentException {
    return getValue();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Constant constant = (Constant) o;
    return Double.compare(constant.value, value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), value);
  }

  @Override
  public String toString() {
    return Double.toString(value);
  }


}
