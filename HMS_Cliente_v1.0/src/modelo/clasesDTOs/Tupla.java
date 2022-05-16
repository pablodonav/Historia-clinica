/**
 * Tupla.java
 * Pablo Doñate y Adnana Dragut (05/2021). 
 *   
 */
package modelo.clasesDTOs;

/**
 *  Tupla genérica de dos objetos.
 * 
 */
public class Tupla<A, B> {
  public final A a;
  public final B b;

  /**
   *  Construye una tupla.
   * 
   */   
  public Tupla(A a, B b) { 
    this.a = a; 
    this.b = b; 
  }
}  