/**
 * Tupla.java
 * Adnana Catrinel Dragut 
 * v2.0 21/04/2022.
 */
package modelo.clasesDTOs;

/**
 * Clase que permite crear una tupla genérica con dos objetos de cualquier tipo de clase.
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