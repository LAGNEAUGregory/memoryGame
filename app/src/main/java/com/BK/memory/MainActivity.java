/**
 * @file MainActivity.java
 * @brief Implémente le jeu de mémoire avec Android.
 */

package com.BK.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * @class MainActivity
 * @brief Classe principale pour gérer le jeu de mémoire.
 */
public class MainActivity extends AppCompatActivity {
    int[] memory; /**< Tableau pour stocker les valeurs des cartes. */
    Button[] btn; /**< Tableau de boutons représentant les cartes. */
    boolean[] revealed; /**< Indique quelles cartes sont actuellement visibles. */
    int firstCard = -1; /**< Index de la première carte sélectionnée. */
    int secondCard = -1; /**< Index de la deuxième carte sélectionnée. */
    boolean isProcessing = false; /**< Bloque les clics lorsque les cartes sont en cours de traitement. */

    private int score = 0; /**< Score actuel du joueur. */
    private TextView scoreText; /**< Composant pour afficher le score. */

    /**
     * @brief Méthode appelée lors de la création de l'activité.
     * @param savedInstanceState État sauvegardé de l'application, s'il y en a un.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreText = findViewById(R.id.scoreText);
        memory = new int[20];
        revealed = new boolean[20];
        btn = new Button[20];

        // Initialisation des valeurs des cartes (10 paires)
        for (int i = 0; i < 10; i++) {
            memory[i] = i;
            memory[i + 10] = i;
        }

        // Mélanger les cartes
        melangeCartes(memory);

        // Initialisation des boutons et des événements
        btn = new Button[20];
        btn[0] = findViewById(R.id.btn0);
        btn[1] = findViewById(R.id.btn1);
        btn[2] = findViewById(R.id.btn2);
        btn[3] = findViewById(R.id.btn3);
        btn[4] = findViewById(R.id.btn4);
        btn[5] = findViewById(R.id.btn5);
        btn[6] = findViewById(R.id.btn6);
        btn[7] = findViewById(R.id.btn7);
        btn[8] = findViewById(R.id.btn8);
        btn[9] = findViewById(R.id.btn9);
        btn[10] = findViewById(R.id.btn10);
        btn[11] = findViewById(R.id.btn11);
        btn[12] = findViewById(R.id.btn12);
        btn[13] = findViewById(R.id.btn13);
        btn[14] = findViewById(R.id.btn14);
        btn[15] = findViewById(R.id.btn15);
        btn[16] = findViewById(R.id.btn16);
        btn[17] = findViewById(R.id.btn17);
        btn[18] = findViewById(R.id.btn18);
        btn[19] = findViewById(R.id.btn19);

        for (int i = 0; i < 20; i++) {
            btn[i].setTag(i);
            btn[i].setText("?"); // Cacher les valeurs au départ
            btn[i].setOnClickListener(this::onClick);
            revealed[i] = false; // Toutes les cartes sont masquées au départ
        }
    }

    /**
     * @brief Mélange les éléments d'un tableau.
     * @param array Tableau d'entiers à mélanger.
     */
    private void melangeCartes(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    /**
     * @brief Gère les clics sur les boutons représentant les cartes.
     * @param v Vue du bouton cliqué.
     */
    public void onClick(View v) {
        if (isProcessing) return; // Empêcher les clics multiples pendant le traitement

        int clickedIndex = (int) v.getTag();

        // Vérifier si la carte est déjà révélée ou si elle est la première sélectionnée
        if (revealed[clickedIndex] || clickedIndex == firstCard) return;

        // Révéler la carte
        btn[clickedIndex].setText(String.valueOf(memory[clickedIndex]));

        if (firstCard == -1) {
            // Première carte sélectionnée
            firstCard = clickedIndex;
        } else if (secondCard == -1) {
            // Deuxième carte sélectionnée
            secondCard = clickedIndex;

            // Vérifier la correspondance des cartes
            if (memory[firstCard] == memory[secondCard]) {
                // Les cartes correspondent
                Toast.makeText(this, "Match trouvé!", Toast.LENGTH_SHORT).show();
                revealed[firstCard] = true;
                revealed[secondCard] = true;
                btn[firstCard].setEnabled(false);
                btn[secondCard].setEnabled(false);
                score++; // Incrémenter le score
                scoreText.setText("Score: " + score); // Mettre à jour l'affichage du score
                firstCard = -1;
                secondCard = -1;
            } else {
                // Les cartes ne correspondent pas
                isProcessing = true;
                new Handler().postDelayed(() -> {
                    btn[firstCard].setText("?"); // Masquer la première carte
                    btn[secondCard].setText("?"); // Masquer la deuxième carte
                    firstCard = -1;
                    secondCard = -1;
                    isProcessing = false;
                }, 1000); // Attente de 1 seconde
            }
        }
    }

}
