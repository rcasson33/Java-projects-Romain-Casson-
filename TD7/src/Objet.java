import java.util.Random;

public class Objet {

    static final Random RANDOM = new Random(42);

    private String nom;
    private int poids;
    private int valeur;

    public Objet(String nom, int poids, int valeur) {
        this.nom = nom;
        this.poids = poids;
        this.valeur = valeur;
    }

    /** @return une description de l'objet */
    public String toString() {
        return this.nom;
    }

    /** @return le poids de l'objet en grammes */
    public int poids() {
        return this.poids;
    };

    /** @return la valeur de l'objet en sous */
    public int valeur() {
        return this.valeur;
    };

    public static Objet random() {
        switch (RANDOM.nextInt(16)) {

            case 0:
                return new Objet("une Twingo", 1140000, 1000000);

            case 1:
            case 2:
                return new Objet("un pansement", 1, 2);

            case 3:
                return new Objet("une potion étiquetée CH₃COOH", 130, 50);
            case 4:
                return new Objet("une potion étiquetée H₂O₂", 130, 100);
            case 5:
                return new Objet("une potion non étiquetée", 130, 500);

            case 6:
                return new Objet("un caillou", 4000, 2);

            case 7:
                return new Objet("une corde elfique", 20, 300);

            case 8:
                return new Objet("un parchemin de téléportation", 20, 300);
            case 9:
                return new Objet("un parchemin de Java", 20, 300);

            case 10:
                return new Objet("une petite piece d'or", 42, 100);
            case 11:
                return new Objet("une piece d'or", 84, 200);
            case 12:
                return new Objet("une grosse piece d'or", 210, 500);

            case 13:
                return new Objet("un bouclier", 8000, 600);
            case 14:
                return new Objet("un bouclier magique", 2000, 600);

            case 15:
                return new Objet("une épée magique", 2000, 1000);

            default:
                throw new AssertionError();
        }
    }

}
