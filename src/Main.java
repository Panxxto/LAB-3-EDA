import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static class Game
    {
        private String name;
        private String category;
        private int price;
        private int quality;

        public Game(String name, String category, int price, int quality)
        {
            this.name = name;
            this.category = category;
            this.price = price;
            this.quality = quality;
        }
        String getName()
        {
            return name;
        }
        String getCategory()
        {
            return category;
        }
        int getPrice()
        {
            return price;
        }
        int getQuality()
        {
            return quality;
        }

    }

    public static class Dataset
    {
        private ArrayList<Game> games;
        private String sortedByAttribute = " ";


        private void bubbleSort(Comparator<Game> comp) {
            for (int i = 0; i < games.size() - 1; i++) {
                for (int j = 0; j < games.size() - i - 1; j++) {
                    if (comp.compare(games.get(j), games.get(j + 1)) > 0) {
                        Game temp = games.get(j);
                        games.set(j, games.get(j + 1));
                        games.set(j + 1, temp);
                    }
                }
            }
        }

        private void insertionSort(Comparator<Game> comp) {
            for (int i = 1; i < games.size(); i++) {
                Game key = games.get(i);
                int j = i - 1;
                while (j >= 0 && comp.compare(games.get(j), key) > 0) {
                    games.set(j + 1, games.get(j));
                    j--;
                }
                games.set(j + 1, key);
            }
        }

        private void selectionSort(Comparator<Game> comp) {
            for (int i = 0; i < games.size() - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < games.size(); j++) {
                    if (comp.compare(games.get(j), games.get(minIdx)) < 0) {
                        minIdx = j;
                    }
                }
                Game temp = games.get(i);
                games.set(i, games.get(minIdx));
                games.set(minIdx, temp);
            }
        }

        private ArrayList<Game> mergeSort(ArrayList<Game> list, Comparator<Game> comp) {
            if (list.size() <= 1) return list;
            int mid = list.size() / 2;
            ArrayList<Game> left = mergeSort(new ArrayList<>(list.subList(0, mid)), comp);
            ArrayList<Game> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())), comp);
            return merge(left, right, comp);
        }

        private ArrayList<Game> merge(ArrayList<Game> left, ArrayList<Game> right, Comparator<Game> comp) {
            ArrayList<Game> result = new ArrayList<>();
            int i = 0, j = 0;
            while (i < left.size() && j < right.size()) {
                if (comp.compare(left.get(i), right.get(j)) <= 0) {
                    result.add(left.get(i++));
                } else {
                    result.add(right.get(j++));
                }
            }
            while (i < left.size()) result.add(left.get(i++));
            while (j < right.size()) result.add(right.get(j++));
            return result;
        }

        private void quickSort(int low, int high, Comparator<Game> comp) {

            int[] stack = new int[high - low + 1];
            int top = -1;

            stack[++top] = low;
            stack[++top] = high;

            while (top >= 0) {
                high = stack[top--];
                low = stack[top--];

                int pivotIndex = partition(low, high, comp);


                if (pivotIndex - 1 > low) {
                    stack[++top] = low;
                    stack[++top] = pivotIndex - 1;
                }


                if (pivotIndex + 1 < high) {
                    stack[++top] = pivotIndex + 1;
                    stack[++top] = high;
                }
            }
        }

        private int partition(int low, int high, Comparator<Game> comp) {

            int mid = low + (high - low) / 2;

            if (comp.compare(games.get(mid), games.get(low)) < 0) {
                Collections.swap(games, low, mid);
            }
            if (comp.compare(games.get(high), games.get(low)) < 0) {
                Collections.swap(games, low, high);
            }
            if (comp.compare(games.get(mid), games.get(high)) < 0) {
                Collections.swap(games, mid, high);
            }

            Game pivot = games.get(high);
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (comp.compare(games.get(j), pivot) <= 0) {
                    i++;
                    Collections.swap(games, i, j);
                }
            }

            Collections.swap(games, i + 1, high);
            return i + 1;
        }


        public Dataset(ArrayList<Game> games)
        {
            this.games = games;
        }
        public void setSortedByAttribute(String attribute)
        {
            sortedByAttribute = attribute;
        }

        public ArrayList<Game> getGamesByPrice(int price)
        {
            long startTime = System.currentTimeMillis();

            ArrayList<Game> prueba = new ArrayList<>();

            if(sortedByAttribute.equals("price"))
            {
                int left = 0;
                int right = prueba.size() - 1;
                while(left <= right)
                {
                    int mid = left + (right - left) / 2;
                    int mid_price = games.get(mid).getPrice();
                    if(mid_price == price)
                    {
                        int i = mid;
                        while( i >= 0 && games.get(i).getPrice() == price)
                        {
                            i--;
                        }
                        i++;
                        while (i <= games.size() && games.get(i).getPrice() == price) {
                            prueba.add(games.get(i));
                            i++;
                        }

                        break;
                    }
                    if(mid_price < price)
                    {
                        left= mid + 1;
                    }
                    else{
                        right= mid - 1;
                    }
                }
            }
            else
            {
                for(int i = 0; i < games.size(); i++)
                {
                    if(games.get(i).getPrice() == price)
                    {
                        prueba.add(games.get(i));
                    }
                }

            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            String searchType = sortedByAttribute.equals("price") ? "Busqueda Binaria" : "Busqueda Lineal";
            System.out.println("getGamesByPrice (" + searchType + ") tiempo: " + duration + " ms");

            return prueba;
        }

        public ArrayList<Game> getGamesByPrice(int lowerPrice , int higherPrice)
        {
            long startTime = System.currentTimeMillis();

            ArrayList<Game> Prueba = new ArrayList<>();
            if(sortedByAttribute.equals("price"))
            {
                int left = 0;
                int right = games.size() - 1;
                while(left <= right) {
                    int mid = left + (right - left) / 2;
                    int mid_price = games.get(mid).getPrice();
                    if (mid_price < lowerPrice) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                    for (int i = left; i < games.size(); i++) {
                        int juego = games.get(i).getPrice();
                        if (juego > higherPrice) {
                            break;
                        }
                        Prueba.add(games.get(i));
                    }

                }
            }
            else
            {
                for (int i = 0; i < games.size(); i++)
                {
                    int price = games.get(i).getPrice();

                    if(price >= lowerPrice && price <= higherPrice)
                    {
                        Prueba.add(games.get(i));
                    }
                }
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            String searchType = sortedByAttribute.equals("price") ? "Busqueda Binaria" : "Busqueda Lineal";
            System.out.println("getGamesByPriceRange (" + searchType + ") tiempo: " + duration + " ms");

            return Prueba;
        }

        public ArrayList<Game> getGamesByCategory(String category)
        {
            long startTime = System.currentTimeMillis();

            ArrayList<Game> Prueba = new ArrayList<>();
            int left = 0;
            int right = games.size() - 1;
            if(sortedByAttribute.equals("category"))
            {
                while(left <= right)
                {
                    int mid = left + (right - left) / 2;
                    String midCategory = games.get(mid).getCategory();

                    if(midCategory.equals(category))
                    {
                        int i = mid;
                        while( i >= 0 && games.get(i).getCategory().equals(category))
                        {
                            i--;
                        }
                        i++;
                        while (i <= games.size() && games.get(i).getCategory().equals(category))
                        {
                            Prueba.add(games.get(i));
                            i++;
                        }
                        break;
                    }
                    if(midCategory.compareTo(category) < 0)
                    {
                        left = mid + 1;
                    }
                    else
                    {
                        right = mid - 1;
                    }
                }
            }
            else
            {
                for(int i = 0; i < games.size(); i++)
                {
                    if(games.get(i).getCategory().equals(category))
                    {
                        Prueba.add(games.get(i));
                    }
                }
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            String searchType = sortedByAttribute.equals("category") ? "Busqueda Binaria" : "Busqueda Lineal";
            System.out.println("getGamesByCategory (" + searchType + ") tiempo: " + duration + " ms");

            return Prueba;
        }

        public ArrayList<Game> getGamesByQuality(int quality)
        {
            long startTime = System.currentTimeMillis();

            ArrayList<Game> Prueba = new ArrayList<>();

            int left = 0;
            int right = games.size() - 1;
            if(sortedByAttribute.equals("quality"))
            {
                while (left <= right)
                {
                    int mid = left + (right - left) / 2;
                    int mid_quality = games.get(mid).getQuality();

                    if (mid_quality == quality)
                    {
                        int i = mid;
                        while( i >= 0 && games.get(mid).getQuality() == quality)
                        {
                            i--;
                        }
                        i++;
                        while (i <= games.size() && games.get(i).getQuality() == quality)
                        {
                            Prueba.add(games.get(i));
                            i++;
                        }

                    }
                    if(mid_quality < quality)
                    {
                        left = mid + 1;
                    }
                    else
                    {
                        right = mid - 1;
                    }
                }
            }
            else
            {
                for(int i = 0; i < games.size(); i++)
                {
                    if(games.get(i).quality == quality)
                    {
                        Prueba.add(games.get(i));
                    }
                }
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            String searchType = sortedByAttribute.equals("quality") ? "Busqueda Binaria" : "Busqueda Lineal";
            System.out.println("getGamesByQuality (" + searchType + ") tiempo: " + duration + " ms");

            return Prueba;
        }

        public void sortByAlgorithm(String algorithm, String attribute)
        {
            if(!attribute.equals("price") && !attribute.equals("quality") && !attribute.equals("category"))
            {
                attribute = "price";
            }
            sortedByAttribute = attribute;

            Comparator<Game> comparador;
            switch (attribute)
            {
                case "quality":
                    comparador = Comparator.comparingInt(Game::getQuality);
                    break;
                case "category":
                    comparador = Comparator.comparing(Game::getCategory);
                    break;
                default:
                    comparador = Comparator.comparingInt(Game::getPrice);
            }

            long startTime = System.currentTimeMillis();

            switch (algorithm)
            {
                case "bubbleSort":
                    bubbleSort(comparador);
                    break;
                case "insertionSort":
                    insertionSort(comparador);
                    break;
                case "selectionSort":
                    selectionSort(comparador);
                    break;
                case "mergeSort":
                    games = mergeSort(games,comparador);
                    break;
                case "quickSort":
                    quickSort(0,games.size() - 1 ,comparador);
                    break;
                default:
                    Collections.sort(games,comparador);
                    sortedByAttribute = "CollectionsSort";
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println(algorithm + " ordenando por " + attribute + " - Tiempo: " + duration + " ms");
        }

    }
    public class generateData
    {
        static String[] palabras = {"Dragon", "Quest", "Empire", "Hentai", "Destruccion", "Galaxy", "Ciber", "Pixel", "Song", "Danza", "Stop", "Birds", "Aurora", "Tormenta", "Caos", "Legends", "Glitch", "Drift", "Run", "Susurro", "Eclipse", "Neon", "Warrior", "Paint", "Zombie", "Obsidiana", "Fenix", "Susurro", "Infinito", "Fortnite", "Clash", "League","FC","Apex"};
        static String[] categorias = {"Acción", "Aventura", "Estrategia", "RPG", "Deportes", "Survival Horror", "Lucha", "Ritmo", "Musica", "Arcade", "Carreras", "Mundo abierto", "Puzzle", "Dibujo", "Simulación", "BattleRoyale","Online","Sandbox","Shooter"};
        static Random rand = new Random();

        public static ArrayList<Game> generar(int n)
        {
            ArrayList<Game> lista = new ArrayList<>();
            for(int i = 0; i < n; i++)
            {
                String nombre = palabras[rand.nextInt(palabras.length)] + palabras[rand.nextInt(palabras.length)];
                String categoria = categorias[rand.nextInt(categorias.length)];
                int price = rand.nextInt(70001);
                int quality = rand.nextInt(101);
                lista.add(new Game(nombre, categoria, price, quality));

            }
            return lista;
        }
    }


    public static void benchmarkSorting(int datasetSize) {
        System.out.println("\n=== BENCHMARK ORDENAMIENTO - Dataset tamaño: " + datasetSize + " ===");

        String[] algorithms = {"bubbleSort", "insertionSort", "selectionSort", "mergeSort", "quickSort", "collectionsSort"};
        String[] attributes = {"price", "category", "quality"};

        for (String attribute : attributes) {
            System.out.println("\n--- Ordenando por " + attribute + " ---");

            for (String algorithm : algorithms) {
                long totalTime = 0;
                int iterations = 3;

                for (int i = 0; i < iterations; i++) {
                    ArrayList<Game> games = generateData.generar(datasetSize);
                    Dataset dataset = new Dataset(games);

                    long startTime = System.currentTimeMillis();
                    dataset.sortByAlgorithm(algorithm, attribute);
                    long endTime = System.currentTimeMillis();

                    totalTime += (endTime - startTime);
                }

                long averageTime = totalTime / iterations;

                if (averageTime > 300000) {
                    System.out.println(algorithm + ": más de 300 segundos");
                } else {
                    System.out.println(algorithm + ": " + averageTime + " ms (promedio)");
                }
            }
        }
    }


    public static void benchmarkSearch() {
        System.out.println("\n=== BENCHMARK BÚSQUEDA - Dataset tamaño: 1,000,000 ===");

        int datasetSize = 1000000;
        ArrayList<Game> games = generateData.generar(datasetSize);
        Dataset dataset = new Dataset(games);

        System.out.println("\n--- Búsqueda Lineal ---");
        dataset.getGamesByPrice(25000);
        dataset.getGamesByPrice(10000, 50000);
        dataset.getGamesByCategory("Acción");
        dataset.getGamesByQuality(75);

        System.out.println("\n--- Búsqueda Binaria ---");

        dataset.sortByAlgorithm("mergeSort", "price");
        dataset.getGamesByPrice(25000);
        dataset.getGamesByPrice(10000, 50000);

        dataset.sortByAlgorithm("mergeSort", "category");
        dataset.getGamesByCategory("Acción");

        dataset.sortByAlgorithm("mergeSort", "quality");
        dataset.getGamesByQuality(75);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dataset dataset = null;

        while (true) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Generar juegos aleatorios");
            System.out.println("2. Ordenar juegos");
            System.out.println("3. Mostrar todos los juegos");
            System.out.println("4. Buscar juegos por precio exacto");
            System.out.println("5. Buscar juegos por rango de precio");
            System.out.println("6. Buscar juegos por calidad");
            System.out.println("7. Buscar juegos por categoría");
            System.out.println("8. Ejecutar benchmark de ordenamiento");
            System.out.println("9. Ejecutar benchmark de búsqueda");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("¿Cuántos juegos deseas generar?: ");
                    int n = sc.nextInt();
                    sc.nextLine();
                    ArrayList<Game> juegos = Main.generateData.generar(n);
                    dataset = new Main.Dataset(juegos);
                    System.out.println("Juegos generados correctamente.");
                    break;

                case 2:
                    if (dataset == null) {
                        System.out.println("Primero genera los juegos.");
                        break;
                    }

                    System.out.print("Algoritmo (bubbleSort, insertionSort, selectionSort, mergeSort, quickSort): ");
                    String algoritmo = sc.nextLine();

                    System.out.print("Atributo (price, quality, category): ");
                    String atributo = sc.nextLine();

                    dataset.sortByAlgorithm(algoritmo, atributo);
                    System.out.println("Juegos ordenados por " + atributo + " usando " + algoritmo + ".");
                    break;

                case 3:
                    if (dataset == null) {
                        System.out.println("Primero genera los juegos.");
                        break;
                    }

                    System.out.println("\n--- Lista de Juegos ---");
                    ArrayList<Game> todos = dataset.getGamesByPrice(0, 70000);
                    for (int i = 0; i < todos.size(); i++) {
                        Game g = todos.get(i);
                        System.out.println(g.getName() + " | " + g.getCategory() + " | $" + g.getPrice() + " | Calidad: " + g.getQuality());
                    }
                    break;

                case 4:
                    if (dataset == null) {
                        System.out.println("Primero genera los juegos.");
                        break;
                    }
                    System.out.print("Precio exacto: ");
                    int precioExacto = sc.nextInt();
                    sc.nextLine();
                    ArrayList<Game> porPrecio = dataset.getGamesByPrice(precioExacto);
                    for (int i = 0; i < porPrecio.size(); i++) {
                        Game g = porPrecio.get(i);
                        System.out.println(g.getName() + " | $" + g.getPrice());
                    }
                    break;

                case 5:
                    if (dataset == null) {
                        System.out.println("Primero genera los juegos.");
                        break;
                    }
                    System.out.print("Precio mínimo: ");
                    int precioMin = sc.nextInt();
                    System.out.print("Precio máximo: ");
                    int precioMax = sc.nextInt();
                    sc.nextLine();
                    ArrayList<Game> porRango = dataset.getGamesByPrice(precioMin, precioMax);
                    for (int i = 0; i < porRango.size(); i++) {
                        Game g = porRango.get(i);
                        System.out.println(g.getName() + " | $" + g.getPrice());
                    }
                    break;

                case 6:
                    if (dataset == null) {
                        System.out.println("Primero genera los juegos.");
                        break;
                    }
                    System.out.print("Calidad exacta (0-100): ");
                    int calidad = sc.nextInt();
                    sc.nextLine();
                    ArrayList<Game> porCalidad = dataset.getGamesByQuality(calidad);
                    for (int i = 0; i < porCalidad.size(); i++) {
                        Game g = porCalidad.get(i);
                        System.out.println(g.getName() + " | Calidad: " + g.getQuality());
                    }
                    break;

                case 7:
                    if (dataset == null) {
                        System.out.println("Primero genera los juegos.");
                        break;
                    }
                    System.out.print("Categoría: ");
                    String cat = sc.nextLine();
                    ArrayList<Game> porCategoria = dataset.getGamesByCategory(cat);
                    for (int i = 0; i < porCategoria.size(); i++) {
                        Game g = porCategoria.get(i);
                        System.out.println(g.getName() + " | " + g.getCategory());
                    }
                    break;

                case 8:
                    System.out.println("Selecciona el tamaño del dataset:");
                    System.out.println("1. 100 elementos (10²)");
                    System.out.println("2. 10,000 elementos (10⁴)");
                    System.out.println("3. 1,000,000 elementos (10⁶)");
                    int sizeOption = sc.nextInt();
                    sc.nextLine();

                    int size = 100;
                    switch (sizeOption) {
                        case 1: size = 100; break;
                        case 2: size = 10000; break;
                        case 3: size = 1000000; break;
                        default: System.out.println("Opción inválida, usando 100."); break;
                    }

                    benchmarkSorting(size);
                    break;

                case 9:
                    benchmarkSearch();
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    return;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}