package com.grupo2tbd.thinkink.Rest;

/**
 * Created by luis on 12/27/15.
 */
public class Galeria {
    public String tipo;
    public Usuario idUsuario;

    public Galeria(String tipo, int idUsuario) {
        this.tipo = tipo;
        this.idUsuario = new Usuario(idUsuario);
    }

    public class Foto{
        public int idFoto;
        public String fecha;
        public int idUsuario;
        public String nombre;
        public Foto(int idFoto, String fecha, int idUsuario, String nombre) {
            this.idFoto = idFoto;
            this.fecha = fecha;
            this.idUsuario = idUsuario;
            this.nombre = nombre;
        }
    }

    public static class Usuario{
        public int idUsuario;
        public Usuario(int id){
            idUsuario = id;
        }
    }
}