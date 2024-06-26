package cs3500.reversi.model.discs;

import java.util.Objects;

/**
 * Represents a disc.
 */
public class Disc implements IDisc {
  private DiscType type;
  private DiscPosn posn;

  /**
   * Constructs a disc based on type and position.
   *
   * @param t disc type.
   * @param p disc position.
   */
  public Disc(DiscType t, DiscPosn p) {
    this.type = t;
    this.posn = p;
  }

  /**
   * Constructs disc based on type.
   *
   * @param t of disc
   */
  public Disc(DiscType t) {
    this.type = t;
    this.posn = null;
  }

  public Disc(DiscPosn p) {
    this.posn = p;
  }

  @Override
  public String toString() {
    return this.type.toString() + " ";
  }


  @Override
  public DiscType getType() {
    return this.type;
  }

  @Override
  public DiscPosn getPosn() {
    return this.posn;
  }

  @Override
  public boolean sameType(DiscType other) {
    return this.type == other;
  }

  @Override
  public Disc updateType(DiscType t) {
    this.type = t;
    return this;
  }

  @Override
  public int getQ() {
    return this.posn.getQ();
  }

  @Override
  public int getR() {
    return this.posn.getR();
  }

  @Override
  public int getZ() {
    return this.posn.getZ();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Disc)) {
      return false;
    }
    Disc other = (Disc) o;
    return this.posn.equals(other.posn) && this.type == other.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.posn, this.type);
  }


}