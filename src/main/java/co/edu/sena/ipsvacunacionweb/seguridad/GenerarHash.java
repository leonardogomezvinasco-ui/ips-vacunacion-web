package co.edu.sena.ipsvacunacionweb.seguridad;

/** Genera un hash para crear manualmente la cuenta administradora inicial. */
public final class GenerarHash {
    private GenerarHash() { }
    public static void main(String[] args) {
        if (args.length != 0) {
            System.err.println("Ejecute GenerarHash sin argumentos; la contraseña se solicitará de forma interactiva.");
            System.exit(2);
        }
        java.io.Console consola=System.console();
        if(consola==null){System.err.println("Abra una consola interactiva para ingresar la contraseña de manera segura.");System.exit(2);}
        char[] clave=consola.readPassword("Contraseña (mínimo 12 caracteres): ");
        if(clave==null){System.exit(2);}
        try { System.out.println(HashContrasena.crear(new String(clave))); }
        finally { java.util.Arrays.fill(clave,'\0'); }
    }
}
