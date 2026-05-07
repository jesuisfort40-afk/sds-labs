package com.sdslabs.phpmastery.model

data class Lesson(
    val index: Int,
    val title: String,
    val description: String,
    val htmlContent: String,
    val xpReward: Int = 20
)

data class Module(
    val index: Int,
    val number: String,
    val title: String,
    val description: String,
    val tag: String,
    val tagColor: String, // "cyan", "purple", "green", "amber"
    val isCompleted: Boolean = false,
    val isLocked: Boolean = false,
    val lessonStartIndex: Int = 0
)

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class Challenge(
    val title: String,
    val description: String,
    val difficulty: String, // "EASY", "MEDIUM", "HARD"
    val xpReward: Int,
    val starterCode: String
)

object LessonData {

    val modules = listOf(
        Module(0, "01", "Introduction à PHP",
            "Syntaxe de base, variables, affichage et structure d'un fichier PHP.",
            "Complété", "green", isCompleted = true),
        Module(1, "02", "Variables & Types",
            "Types de données, tableaux, conversion de types et manipulation de chaînes.",
            "Complété", "green", isCompleted = true),
        Module(2, "03", "Structures de Contrôle",
            "if/else, switch, boucles for, while, foreach — contrôlez le flux.",
            "En cours", "cyan"),
        Module(3, "04", "Fonctions",
            "Déclaration, paramètres, valeurs de retour, fonctions anonymes et closures.",
            "Intermédiaire", "purple"),
        Module(4, "05", "Tableaux Avancés",
            "array_map, array_filter, usort, array_reduce et plus.",
            "Intermédiaire", "purple", isLocked = true),
        Module(5, "06", "POO en PHP",
            "Classes, objets, héritage, interfaces, traits et design patterns.",
            "Avancé", "amber", isLocked = true),
        Module(6, "07", "PHP & Base de données",
            "PDO, requêtes préparées, MySQL, sécurité SQL injection.",
            "Avancé", "amber", isLocked = true),
        Module(7, "08", "Projet Final",
            "Construire une API REST complète avec authentification JWT.",
            "Expert", "amber", isLocked = true)
    )

    val lessons = listOf(
        Lesson(0, "Conditions if / else / elseif",
            "Contrôlez le flux d'exécution de votre programme selon des conditions booléennes.",
            """
<h2>Conditions if / else</h2>
<p>En PHP, la structure <strong>if/else</strong> permet d'exécuter des blocs de code différents en fonction d'une condition. C'est la base de toute logique de programmation.</p>

<h3>Syntaxe de base</h3>
<pre><code class="language-php">&lt;?php
${'$'}age = 18;

if (${'$'}age >= 18) {
    echo "Vous êtes majeur.";
} elseif (${'$'}age >= 13) {
    echo "Vous êtes adolescent.";
} else {
    echo "Vous êtes enfant.";
}
?&gt;</code></pre>

<div class="explain-box">
<strong>💡 Comment ça marche</strong><br>
PHP évalue la condition entre parenthèses. Si elle est <strong>vraie (true)</strong>, le premier bloc s'exécute. Sinon, PHP passe au <strong>elseif</strong> suivant, puis au <strong>else</strong> final si aucune condition n'est vraie.
</div>

<h3>Opérateur ternaire</h3>
<pre><code class="language-php">// Syntaxe courte : condition ? vrai : faux
${'$'}statut = (${'$'}age >= 18) ? "majeur" : "mineur";
echo ${'$'}statut; // Affiche: majeur

// Null coalescing (PHP 7+)
${'$'}nom = ${'$'}_GET['nom'] ?? "Inconnu";</code></pre>

<div class="note-box">⚠️ <strong>Attention :</strong> PHP est faiblement typé. <code>0 == false</code> est vrai. Utilisez <code>===</code> pour comparer le type ET la valeur.</div>
            """.trimIndent()
        ),
        Lesson(1, "Switch & Match",
            "Gérez plusieurs cas avec switch et l'expression match de PHP 8.",
            """
<h2>Switch & Match Expression</h2>
<p>Quand vous avez <strong>plusieurs conditions sur la même variable</strong>, <strong>switch</strong> est plus lisible qu'une série de if/elseif. PHP 8 introduit <strong>match</strong>, plus puissant et sans les pièges de switch.</p>

<h3>Switch classique</h3>
<pre><code class="language-php">${'$'}jour = "lundi";

switch (${'$'}jour) {
    case "lundi":
    case "mardi":
        echo "Début de semaine";
        break;
    case "vendredi":
        echo "Presque le week-end !";
        break;
    default:
        echo "Milieu de semaine";
}</code></pre>

<h3>Match (PHP 8+) — recommandé</h3>
<pre><code class="language-php">${'$'}status = 404;

${'$'}message = match(${'$'}status) {
    200 => "OK",
    404 => "Non trouvé",
    500 => "Erreur serveur",
    default => "Statut inconnu"
};

echo ${'$'}message; // Non trouvé</code></pre>

<div class="explain-box">
<strong>✅ Avantages de match vs switch</strong><br>
<strong>match</strong> utilise une comparaison stricte (===), retourne une valeur directement, n'a pas besoin de break, et lève une erreur si aucun cas ne correspond.
</div>
            """.trimIndent()
        ),
        Lesson(2, "Boucles for & while",
            "Répétez des opérations avec for, while et do-while.",
            """
<h2>Boucles for & while</h2>
<p>PHP propose plusieurs structures de boucle. <strong>for</strong> est idéal quand on connaît le nombre d'itérations à l'avance. <strong>while</strong> continue tant qu'une condition est vraie. <strong>do...while</strong> s'exécute au moins une fois.</p>

<h3>Boucle for</h3>
<pre><code class="language-php">// for(initialisation; condition; incrément)
for (${'$'}i = 1; ${'$'}i <= 5; ${'$'}i++) {
    echo "Itération n°" . ${'$'}i . "\n";
}
// Affiche: Itération n°1, n°2, ... n°5

// Décompte inverse
for (${'$'}i = 10; ${'$'}i > 0; ${'$'}i -= 2) {
    echo ${'$'}i . " ";
}
// Affiche: 10 8 6 4 2</code></pre>

<h3>Boucle while</h3>
<pre><code class="language-php">${'$'}tentatives = 0;

while (${'$'}tentatives < 3) {
    echo "Tentative " . (${'$'}tentatives + 1) . "\n";
    ${'$'}tentatives++;
}

// do...while : s'exécute AU MOINS une fois
${'$'}x = 0;
do {
    echo "Exécuté !";
    ${'$'}x++;
} while (${'$'}x < 1);</code></pre>

<div class="note-box">⚠️ <strong>Boucle infinie :</strong> Assurez-vous toujours que la condition devient <code>false</code> à un moment. Une boucle infinie bloquera votre serveur !</div>
            """.trimIndent()
        ),
        Lesson(3, "foreach sur tableaux",
            "Itérez sur des tableaux avec foreach — la boucle la plus utilisée en PHP.",
            """
<h2>foreach — La boucle PHP essentielle</h2>
<p><strong>foreach</strong> est conçu spécifiquement pour les tableaux. Il est plus lisible que for quand on n'a pas besoin de l'index, et gère automatiquement les tableaux associatifs.</p>

<h3>Tableau indexé</h3>
<pre><code class="language-php">${'$'}langages = ["PHP", "JavaScript", "Python", "Go"];

foreach (${'$'}langages as ${'$'}lang) {
    echo "Langage : " . ${'$'}lang . "\n";
}

// Avec l'index
foreach (${'$'}langages as ${'$'}index => ${'$'}lang) {
    echo ${'$'}index . ": " . ${'$'}lang . "\n";
}</code></pre>

<h3>Tableau associatif</h3>
<pre><code class="language-php">${'$'}utilisateur = [
    "nom"    => "Diallo",
    "email"  => "diallo@sds.labs",
    "niveau" => "Expert"
];

foreach (${'$'}utilisateur as ${'$'}cle => ${'$'}valeur) {
    echo ${'$'}cle . ": " . ${'$'}valeur . "\n";
}
// nom: Diallo
// email: diallo@sds.labs
// niveau: Expert</code></pre>
            """.trimIndent()
        ),
        Lesson(4, "break & continue",
            "Contrôlez finement l'exécution des boucles.",
            """
<h2>break & continue</h2>
<p><strong>break</strong> stoppe une boucle complètement. <strong>continue</strong> saute à l'itération suivante.</p>

<h3>break — stoppe la boucle</h3>
<pre><code class="language-php">for (${'$'}i = 0; ${'$'}i < 10; ${'$'}i++) {
    if (${'$'}i === 5) break;
    echo ${'$'}i . " ";
}
// Affiche: 0 1 2 3 4</code></pre>

<h3>continue — passe à l'itération suivante</h3>
<pre><code class="language-php">for (${'$'}i = 0; ${'$'}i < 10; ${'$'}i++) {
    if (${'$'}i % 2 === 0) continue;
    echo ${'$'}i . " ";
}
// Affiche: 1 3 5 7 9 (impairs seulement)</code></pre>

<h3>break avec niveau</h3>
<pre><code class="language-php">// break 2 sort de 2 niveaux de boucles imbriquées
for (${'$'}i = 0; ${'$'}i < 3; ${'$'}i++) {
    for (${'$'}j = 0; ${'$'}j < 3; ${'$'}j++) {
        if (${'$'}j === 1) break 2;
        echo "${'$'}i,${'$'}j ";
    }
}
// Affiche: 0,0</code></pre>
            """.trimIndent()
        )
    )

    val quizQuestions = listOf(
        QuizQuestion(
            "Quelle syntaxe PHP est correcte pour déclarer une variable ?",
            listOf("var nom = 'PHP';", "\$nom = 'PHP';", "let nom = 'PHP';", "string \$nom = 'PHP';"),
            1,
            "En PHP, toutes les variables commencent par \$. La déclaration de type est optionnelle."
        ),
        QuizQuestion(
            "Quel opérateur compare à la fois la valeur ET le type en PHP ?",
            listOf("==", "!=", "===", "<=>"),
            2,
            "=== est l'opérateur de comparaison stricte. 0 == false est true, mais 0 === false est false."
        ),
        QuizQuestion(
            "Comment afficher 'Bonjour' en PHP ?",
            listOf("print('Bonjour')", "echo 'Bonjour';", "console.log('Bonjour')", "Les deux premières réponses sont correctes"),
            3,
            "echo et print fonctionnent tous les deux. echo est légèrement plus rapide et peut afficher plusieurs valeurs séparées par des virgules."
        ),
        QuizQuestion(
            "Quelle est la différence entre match et switch en PHP 8 ?",
            listOf(
                "Il n'y a aucune différence",
                "match utilise === et retourne une valeur, switch utilise ==",
                "match est plus lent",
                "switch supporte plus de types"
            ),
            1,
            "match utilise une comparaison stricte (===), retourne directement une valeur, et lance une UnhandledMatchError si aucun cas ne correspond."
        ),
        QuizQuestion(
            "Que fait 'continue' dans une boucle PHP ?",
            listOf(
                "Arrête la boucle complètement",
                "Passe à l'itération suivante",
                "Repart depuis le début",
                "Lance une exception"
            ),
            1,
            "continue saute le reste du code de l'itération actuelle et passe à la prochaine itération. break arrête la boucle entière."
        ),
        QuizQuestion(
            "Quelle boucle est la plus adaptée pour parcourir un tableau associatif ?",
            listOf("for", "while", "foreach", "do...while"),
            2,
            "foreach est conçu spécifiquement pour les tableaux. La syntaxe 'foreach (\$tab as \$cle => \$valeur)' donne accès aux clés et valeurs."
        )
    )

    val challenges = listOf(
        Challenge(
            "FizzBuzz Classic",
            "Affichez les nombres 1 à 100, mais 'Fizz' pour multiples de 3, 'Buzz' pour 5, 'FizzBuzz' pour les deux.",
            "FACILE",
            10,
            "<?php\n// FizzBuzz - Complétez le défi !\nfor (\$i = 1; \$i <= 100; \$i++) {\n    // TODO: logique FizzBuzz ici\n    echo \$i . \"\\n\";\n}\n?>"
        ),
        Challenge(
            "Palindrome Checker",
            "Vérifiez si un mot est un palindrome sans utiliser strrev().",
            "MOYEN",
            25,
            "<?php\n// Palindrome Checker\nfunction isPalindrome(\$str) {\n    // TODO: sans utiliser strrev()\n    return false;\n}\n\necho isPalindrome(\"radar\") ? \"Oui\" : \"Non\";\n?>"
        ),
        Challenge(
            "Fibonacci Suite",
            "Générez les N premiers termes de la suite de Fibonacci avec récursivité.",
            "MOYEN",
            30,
            "<?php\n// Suite de Fibonacci récursive\nfunction fibonacci(\$n) {\n    // TODO: implémentez la récursivité\n    return \$n;\n}\n\nfor (\$i = 0; \$i < 10; \$i++) {\n    echo fibonacci(\$i) . \" \";\n}\n?>"
        ),
        Challenge(
            "Trier sans sort()",
            "Implémentez le tri à bulles (bubble sort) manuellement.",
            "DIFFICILE",
            50,
            "<?php\n// Bubble Sort\nfunction bubbleSort(\$arr) {\n    // TODO: implémentez le tri à bulles\n    return \$arr;\n}\n\n\$numbers = [64, 34, 25, 12, 22, 11, 90];\n\$sorted = bubbleSort(\$numbers);\necho implode(\", \", \$sorted);\n?>"
        ),
        Challenge(
            "Compteur de mots",
            "Comptez la fréquence de chaque mot dans une chaîne donnée.",
            "FACILE",
            15,
            "<?php\n// Compteur de mots\n\$texte = \"le chat mange le poisson le chat\";\n// TODO: compter chaque mot\n?>"
        ),
        Challenge(
            "Mini calculatrice",
            "Parsez une expression arithmétique simple comme '3 + 5 * 2'.",
            "DIFFICILE",
            60,
            "<?php\n// Mini calculatrice\nfunction calculate(\$expression) {\n    // TODO: parser l'expression\n    return 0;\n}\n\necho calculate(\"3 + 5\"); // 8\necho calculate(\"10 - 3\"); // 7\n?>"
        )
    )
}
