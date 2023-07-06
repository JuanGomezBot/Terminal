import java.util.*;

public class Terminal {
    public Scanner scanner = new Scanner(System.in);

    public int menu() {
        boolean algo = true;
        int choice = 0;

        while (algo == true) {
            System.out.println("Bienvenido a la base de datos");
            System.out.println("1. Crear usuario\n 2. Acceder usuario\n 3. Eliminar usuario\n  5. Salir\n");

            try {
                choice = scanner.nextInt();
                if ((choice > 5 || choice < 0)) {
                    System.out.println("Por favor ponga un numero adecuado");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Porfa pon un numero");
                continue;
            }
            algo = false;
        }

        return choice;
    }

    public Map<String, List<String>> datab = new HashMap<>();
    public List<String> datos = new ArrayList<String>();
    public Map<String, String> userdata = new HashMap<String, String>();

    public void Crearusuario() {

        System.out.println("Escribe un nombre de usuario: ");
        String user = scanner.next();
        System.out.println("Escribe una clave: ");
        String password = scanner.next();

        String hashedpassword = Integer.toString(password.hashCode());
        userdata.put(user, hashedpassword);
        datab.put(user, datos);

    }

    public void eliminarusuario() {
        System.out.println("Por favor digame qué usuario desea eliminar");
        String user = scanner.next();
        datab.remove(user, datab.get(user));

    }

    public int menu1() {
        boolean algo = true;
        int choice = 0;

        while (algo == true) {
            System.out.println("1. agregar datos a la lista\n2. Leer lista\n3. Cerrar lista");

            try {
                choice = scanner.nextInt();
                if ((choice > 3 || choice < 1)) {
                    System.out.println("Por favor ponga un numero adecuado");
                    continue;
                }

            } catch (NumberFormatException e) {
                System.out.println("Porfa pon un numero");

                continue;

            }
            break;
        }

        return choice;

    }

    public void cargarusuario() {
        System.out.println("Escribe tu usuario");
        String user = scanner.next();
        System.out.println("Escribe tu clave");
        String password = scanner.next();
        String h = Integer.toString(password.hashCode());
        if (userdata.containsKey(user) && userdata.containsValue(h)) {
            System.out.println("Bienvenido " + user);
            int choose = menu1();
            if (choose == 1) {
                Adddata(user);
            } else if (choose == 2) {
                readData(user);
            } else if (choose == 3) {
                Close();
            }
        } else {
            System.out.println("Clave incorrecta");
            System.exit(0);
        }

    }

    public void Adddata(String Username) {
        List<String> copy = datab.get(Username);
        while (!scanner.next().equals("salir")) {
            System.out.println("Escribe el dato que quieres poner o escribe salir");
            String a = scanner.next();
            copy.add(a);
            datab.replace(Username, copy);
        }
    }

    public void Close() {
        menu();
    }

    public void readData(String Username) {
        List<String> copy = datab.get(Username);
        for (String string : copy) {
            System.out.println(string);
        }
    }

    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        while (true) {
            int ch = terminal.menu();
            if (ch == 1) {
                terminal.Crearusuario();
            } else if (ch == 2) {
                terminal.cargarusuario();
            } else if (ch == 3) {
                terminal.eliminarusuario();
            } else if (ch == 5) {
                System.exit(0);
            }
        }
    }
}
