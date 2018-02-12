package org.handrinp.diffyq.bool.logical;

import org.handrinp.diffyq.BooleanExpr;
import org.handrinp.diffyq.bool.comparative.GreaterEqualExpr;
import org.handrinp.diffyq.bool.comparative.GreaterExpr;
import org.handrinp.diffyq.bool.comparative.LessEqualExpr;
import org.handrinp.diffyq.bool.comparative.LessExpr;

/**
 * the logical not operator
 * 
 * @author handrinp
 */
public class NotExpr extends BooleanExpr {
  private BooleanExpr expr;

  /**
   * constructs a new not expression corresponding to !expr
   * 
   * @param expr boolean expression to negate
   */
  public NotExpr(BooleanExpr expr) {
    this.expr = expr;
  }

  /**
   * the underlying boolean expression
   * 
   * @return expr boolean expression
   */
  public BooleanExpr getExpr() {
    return expr;
  }

  @Override
  public boolean evaluate(double x) {
    return !expr.evaluate(x);
  }

  @Override
  public BooleanExpr reduce() {
    if (expr instanceof NotExpr) {
      return ((NotExpr) expr).getExpr().reduce();
    }

    if (expr instanceof GreaterExpr) {
      final GreaterExpr gx = (GreaterExpr) expr.reduce();
      return new LessEqualExpr(gx.getLHS(), gx.getRHS());
    }

    if (expr instanceof LessExpr) {
      final LessExpr lx = (LessExpr) expr.reduce();
      return new GreaterEqualExpr(lx.getLHS(), lx.getRHS());
    }

    if (expr instanceof GreaterEqualExpr) {
      final GreaterEqualExpr gex = (GreaterEqualExpr) expr.reduce();
      return new LessExpr(gex.getLHS(), gex.getRHS());
    }

    if (expr instanceof LessEqualExpr) {
      final LessEqualExpr lex = (LessEqualExpr) expr.reduce();
      return new GreaterExpr(lex.getLHS(), lex.getRHS());
    }

    return new NotExpr(expr.reduce());
  }

  @Override
  public String asString() {
    return "!(" + expr.asString() + ")";
  }
}
