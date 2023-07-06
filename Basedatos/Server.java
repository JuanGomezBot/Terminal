
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Server {
    public Scanner scanner = new Scanner(System.in);
    public Duplexer d;

    public Map<String, List<String>> database = new HashMap<>();
    public Map<String, String> userdata = new HashMap<>();

    public void CrearUsuario() throws IOException {
        d.send("Escribe un nombre de usuario: ");
        String user = d.read();
        d.send("Escribe una clave: ");
        String password = d.read();
        String hashedPassword = Integer.toString(password.hashCode());
        userdata.put(user, hashedPassword);
        database.put(user, new ArrayList<>());
    }

    public void EliminarUsuario() throws IOException {
        d.send("Por favor digame qué usuario desea eliminar");
        String user = d.read();
        database.remove(user);
        userdata.remove(user);
    }

    public int menu1() throws IOException {
        int choice = 0;
        while (true) {
            d.send("1. agregar datos a la lista\n2. Leer lista\n3. Cerrar lista");
            try {
                choice = Integer.parseInt(d.read());
                if (choice < 1 || choice > 3) {
                    System.out.println("Por favor ponga un número adecuado");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Por favor pon un número");
            }
        }
        return choice;
    }

    public void cargarUsuario() throws IOException {
        d.send("Escribe tu usuario");
        String user = d.read();
        d.send("Escribe tu clave");
        String password = d.read();
        String hashedPassword = Integer.toString(password.hashCode());
        if (userdata.containsKey(user) && userdata.get(user).equals(hashedPassword)) {
            d.send("Bienvenido " + user);
            int choice = menu1();
            if (choice == 1) {
                AddData(user);
            } else if (choice == 2) {
                readData(user);
            } else if (choice == 3) {
                Close();
            }
        } else {
            d.send("Clave incorrecta");
            System.exit(0);
        }
    }

    public void AddData(String username) throws IOException {
        List<String> data = database.get(username);
        while (true) {
            d.send("Escribe el dato que quieres poner o escribe 'salir' para finalizar");
            String input = d.read();
            if (input.equals("salir")) {
                break;
            }
            data.add(input);
            database.put(username, data);
        }
    }

    public void Close() throws IOException {
        menu();
    }

    public void readData(String username) {
        List<String> data = database.get(username);
        for (String string : data) {
            d.send(string);
        }
    }

    public void menu() throws IOException {
        int choice = 0;
        while (choice != 5) {
            d.send("Bienvenido a la base de datos \n 1. Crear usuario\n 2. Acceder usuario\n 3. Eliminar usuario\n  5. Salir\n");
            try {
                choice = Integer.parseInt(d.read());
                if (choice < 1 || choice > 5) {
                    System.out.println("Por favor ponga un número adecuado");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor pon un número");
                continue;
            }

            if (choice == 1) {
                CrearUsuario();
            } else if (choice == 2) {
                cargarUsuario();
            } else if (choice == 3) {
                EliminarUsuario();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        Duplexer d = new Duplexer(socket);
        Server server = new Server();
        server.d = d;
        server.menu();
    }
}
