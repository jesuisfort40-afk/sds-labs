package com.sdslabs.phpmastery.model

data class Lesson(
    val index: Int,
    val moduleIndex: Int,
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
    val tagColor: String,
    val isCompleted: Boolean = false,
    val isLocked: Boolean = false,
    val lessonStartIndex: Int = 0,
    val lessonCount: Int = 0
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
    val difficulty: String,
    val xpReward: Int,
    val starterCode: String
)

object LessonData {

    val modules = listOf(
        Module(0, "01", "Introduction à PHP",
            "Syntaxe de base, variables, affichage et structure d'un fichier PHP.",
            "Débutant", "green", lessonStartIndex = 0, lessonCount = 5),
        Module(1, "02", "Variables & Types",
            "Types de données, tableaux, conversion de types et manipulation de chaînes.",
            "Débutant", "green", lessonStartIndex = 5, lessonCount = 5),
        Module(2, "03", "Structures de Contrôle",
            "if/else, switch, boucles for, while, foreach — contrôlez le flux.",
            "En cours", "cyan", lessonStartIndex = 10, lessonCount = 5),
        Module(3, "04", "Fonctions",
            "Déclaration, paramètres, valeurs de retour, fonctions anonymes et closures.",
            "Intermédiaire", "purple", isLocked = true, lessonStartIndex = 15, lessonCount = 5),
        Module(4, "05", "Tableaux Avancés",
            "array_map, array_filter, usort, array_reduce et plus.",
            "Intermédiaire", "purple", isLocked = true, lessonStartIndex = 20, lessonCount = 5),
        Module(5, "06", "POO en PHP",
            "Classes, objets, héritage, interfaces, traits et design patterns.",
            "Avancé", "amber", isLocked = true, lessonStartIndex = 25, lessonCount = 5),
        Module(6, "07", "PHP & Base de données",
            "PDO, requêtes préparées, MySQL, sécurité SQL injection.",
            "Avancé", "amber", isLocked = true, lessonStartIndex = 30, lessonCount = 5),
        Module(7, "08", "Projet Final",
            "Construire une API REST complète avec authentification JWT.",
            "Expert", "amber", isLocked = true, lessonStartIndex = 35, lessonCount = 5)
    )

    val lessons = listOf(
        // ── MODULE 01 : Introduction à PHP (index 0-4) ──────────────────────
        Lesson(0, 0, "Qu'est-ce que PHP ?",
            "Découvrez PHP, son histoire et son rôle dans le développement web.",
            """
<h2>Qu'est-ce que PHP ?</h2>
<p>PHP (Hypertext Preprocessor) est un langage de script côté <strong>serveur</strong>, conçu pour le développement web. Créé en 1994 par Rasmus Lerdorf, il propulse aujourd'hui plus de 75% des sites web, dont WordPress, Facebook (à ses débuts) et Wikipedia.</p>

<h3>Comment PHP fonctionne</h3>
<pre><code class="language-php">&lt;?php
// Votre premier script PHP
echo "Bonjour le monde !";
echo "&lt;br&gt;";
echo "PHP version : " . PHP_VERSION;
?&gt;</code></pre>

<div class="explain-box">
<strong>💡 Le cycle PHP</strong><br>
1. Le navigateur envoie une requête au serveur<br>
2. PHP s'exécute sur le serveur<br>
3. PHP génère du HTML<br>
4. Le HTML est envoyé au navigateur
</div>

<h3>Structure d'un fichier PHP</h3>
<pre><code class="language-php">&lt;?php
// Commentaire sur une ligne
/* Commentaire
   multi-lignes */

echo "PHP peut être mélangé avec HTML";
?&gt;
&lt;h1&gt;Titre HTML normal&lt;/h1&gt;</code></pre>

<div class="note-box">⚠️ <strong>Important :</strong> Les fichiers PHP ont l'extension <code>.php</code>. La balise d'ouverture <code>&lt;?php</code> est obligatoire. La balise de fermeture <code>?&gt;</code> est optionnelle en fin de fichier.</div>
            """.trimIndent()
        ),
        Lesson(1, 0, "Variables & Affichage",
            "Déclarez des variables et affichez des données avec echo et print.",
            """
<h2>Variables & Affichage</h2>
<p>En PHP, toutes les variables commencent par <strong>$</strong>. PHP est un langage à typage dynamique : le type est déterminé automatiquement selon la valeur.</p>

<h3>Déclaration de variables</h3>
<pre><code class="language-php">&lt;?php
${'$'}nom = "Alice";           // String
${'$'}age = 25;                // Integer
${'$'}taille = 1.68;           // Float
${'$'}estMajeur = true;        // Boolean
${'$'}valeurNulle = null;      // Null

echo ${'$'}nom;                // Affiche: Alice
echo "&lt;br&gt;";
echo "J'ai " . ${'$'}age . " ans"; // Concaténation avec .
?&gt;</code></pre>

<h3>echo vs print</h3>
<pre><code class="language-php">echo "Rapide et peut afficher plusieurs valeurs";
echo "Un", " ", "deux", " ", "trois";  // OK avec echo

print "print retourne toujours 1";
// ${'$'}resultat = print("test"); // ${'$'}resultat = 1</code></pre>

<div class="explain-box">
<strong>💡 Interpolation de variables</strong><br>
Avec les guillemets doubles, PHP interprète les variables directement dans la chaîne :
<pre><code>${'$'}prenom = "Bob";
echo "Bonjour ${'$'}prenom !";  // Bonjour Bob !
echo 'Bonjour ${'$'}prenom !';  // Bonjour ${'$'}prenom ! (guillemets simples = littéral)</code></pre>
</div>
            """.trimIndent()
        ),
        Lesson(2, 0, "Types de données",
            "Explorez les types string, int, float, bool et null en PHP.",
            """
<h2>Types de données en PHP</h2>
<p>PHP supporte 8 types primitifs. Comprendre les types est essentiel pour éviter les bugs subtils liés au typage faible.</p>

<h3>Les types scalaires</h3>
<pre><code class="language-php">&lt;?php
// String
${'$'}texte = "Bonjour";
${'$'}texte2 = 'Simple quote';
${'$'}multi = "Ligne 1\nLigne 2";   // \n = saut de ligne

// Integer
${'$'}entier = 42;
${'$'}negatif = -10;
${'$'}hexa = 0x1A;   // 26 en hexadécimal

// Float
${'$'}decimal = 3.14;
${'$'}scientifique = 1.5e3;  // 1500

// Boolean
${'$'}vrai = true;
${'$'}faux = false;

// Vérifier le type
var_dump(${'$'}entier);    // int(42)
var_dump(${'$'}decimal);   // float(3.14)
var_dump(${'$'}vrai);      // bool(true)
?&gt;</code></pre>

<h3>Conversion de types</h3>
<pre><code class="language-php">${'$'}chaine = "42abc";
${'$'}nombre = (int) ${'$'}chaine;    // 42 (cast)
${'$'}nombre2 = intval(${'$'}chaine); // 42 (fonction)

echo gettype(${'$'}nombre);  // integer</code></pre>

<div class="note-box">⚠️ PHP convertit automatiquement les types selon le contexte. <code>"5" + 3</code> donne <code>8</code> car PHP convertit "5" en entier. Soyez prudent avec ces conversions implicites !</div>
            """.trimIndent()
        ),
        Lesson(3, 0, "Opérateurs",
            "Maîtrisez les opérateurs arithmétiques, de comparaison et logiques.",
            """
<h2>Opérateurs PHP</h2>
<p>PHP propose une riche collection d'opérateurs pour effectuer des calculs, des comparaisons et des opérations logiques.</p>

<h3>Opérateurs arithmétiques</h3>
<pre><code class="language-php">&lt;?php
${'$'}a = 10; ${'$'}b = 3;

echo ${'$'}a + ${'$'}b;   // 13 — Addition
echo ${'$'}a - ${'$'}b;   // 7  — Soustraction
echo ${'$'}a * ${'$'}b;   // 30 — Multiplication
echo ${'$'}a / ${'$'}b;   // 3.333... — Division
echo ${'$'}a % ${'$'}b;   // 1  — Modulo (reste)
echo ${'$'}a ** ${'$'}b;  // 1000 — Puissance
?&gt;</code></pre>

<h3>Opérateurs de comparaison</h3>
<pre><code class="language-php">var_dump(5 == "5");   // true  — égalité (valeur)
var_dump(5 === "5");  // false — identité (valeur + type)
var_dump(5 != 3);    // true  — différent
var_dump(5 !== "5"); // true  — strictement différent
var_dump(5 > 3);     // true
var_dump(5 <=> 3);   // 1 (spaceship: -1, 0, ou 1)</code></pre>

<h3>Opérateurs logiques</h3>
<pre><code class="language-php">${'$'}x = true; ${'$'}y = false;

var_dump(${'$'}x && ${'$'}y);  // false — ET
var_dump(${'$'}x || ${'$'}y);  // true  — OU
var_dump(!${'$'}x);           // false — NON

// Null coalescing
${'$'}val = ${'$'}inconnu ?? "défaut"; // "défaut" si ${'$'}inconnu est null</code></pre>
            """.trimIndent()
        ),
        Lesson(4, 0, "Tableaux de base",
            "Créez et manipulez des tableaux indexés et associatifs.",
            """
<h2>Tableaux en PHP</h2>
<p>Les tableaux PHP sont très flexibles : ils peuvent contenir n'importe quel type, être indexés numériquement ou par des clés string.</p>

<h3>Tableau indexé</h3>
<pre><code class="language-php">&lt;?php
${'$'}fruits = ["pomme", "banane", "cerise"];
// Ancienne syntaxe : array("pomme", "banane", "cerise")

echo ${'$'}fruits[0];   // pomme
echo ${'$'}fruits[1];   // banane
echo count(${'$'}fruits); // 3

// Ajouter un élément
${'$'}fruits[] = "mangue";
array_push(${'$'}fruits, "kiwi");
?&gt;</code></pre>

<h3>Tableau associatif</h3>
<pre><code class="language-php">${'$'}personne = [
    "nom"   => "Diallo",
    "age"   => 28,
    "ville" => "Dakar"
];

echo ${'$'}personne["nom"];   // Diallo
${'$'}personne["email"] = "diallo@sds.labs"; // Ajout

// Vérifier si une clé existe
if (array_key_exists("age", ${'$'}personne)) {
    echo "Âge : " . ${'$'}personne["age"];
}</code></pre>

<div class="explain-box">
<strong>💡 Fonctions utiles</strong><br>
<code>count()</code> — nombre d'éléments<br>
<code>in_array()</code> — chercher une valeur<br>
<code>array_keys()</code> — toutes les clés<br>
<code>array_values()</code> — toutes les valeurs
</div>
            """.trimIndent()
        ),

        // ── MODULE 02 : Variables & Types (index 5-9) ────────────────────────
        Lesson(5, 1, "Chaînes de caractères",
            "Manipulez les strings avec les fonctions essentielles de PHP.",
            """
<h2>Manipulation de chaînes</h2>
<p>PHP propose plus de 100 fonctions pour travailler avec les chaînes. Voici les plus utilisées.</p>

<h3>Fonctions essentielles</h3>
<pre><code class="language-php">&lt;?php
${'$'}texte = "  Bonjour le Monde  ";

echo strlen(${'$'}texte);           // 20 — longueur
echo strtolower(${'$'}texte);       // "  bonjour le monde  "
echo strtoupper(${'$'}texte);       // "  BONJOUR LE MONDE  "
echo trim(${'$'}texte);             // "Bonjour le Monde"
echo str_replace("Monde","PHP",${'$'}texte); // "  Bonjour le PHP  "
echo substr(${'$'}texte, 2, 7);     // "Bonjour"
?&gt;</code></pre>

<h3>Recherche & vérification</h3>
<pre><code class="language-php">${'$'}email = "user@example.com";

// Chercher une position
${'$'}pos = strpos(${'$'}email, "@"); // 4
if (${'$'}pos !== false) {
    echo "@ trouvé en position " . ${'$'}pos;
}

// Vérifier si contient
if (str_contains(${'$'}email, "@")) { // PHP 8+
    echo "C'est un email valide";
}

// Commencer/finir par
var_dump(str_starts_with(${'$'}email, "user")); // true
var_dump(str_ends_with(${'$'}email, ".com"));   // true</code></pre>

<h3>Diviser & assembler</h3>
<pre><code class="language-php">${'$'}csv = "PHP,Python,JavaScript,Go";
${'$'}langages = explode(",", ${'$'}csv);  // ["PHP","Python","JavaScript","Go"]

${'$'}rejoindre = implode(" | ", ${'$'}langages); // "PHP | Python | JavaScript | Go"</code></pre>
            """.trimIndent()
        ),
        Lesson(6, 1, "Nombres & Math",
            "Travaillez avec les entiers, flottants et les fonctions mathématiques.",
            """
<h2>Nombres & Fonctions Math</h2>
<p>PHP offre un ensemble complet de fonctions mathématiques pour tous vos calculs.</p>

<h3>Fonctions mathématiques</h3>
<pre><code class="language-php">&lt;?php
echo abs(-15);        // 15  — valeur absolue
echo ceil(4.3);       // 5   — arrondi supérieur
echo floor(4.9);      // 4   — arrondi inférieur
echo round(4.5678, 2); // 4.57 — arrondi (2 décimales)
echo max(3, 7, 1, 9); // 9
echo min(3, 7, 1, 9); // 1
echo pow(2, 10);      // 1024
echo sqrt(144);       // 12
echo pi();            // 3.14159...
?&gt;</code></pre>

<h3>Nombres aléatoires</h3>
<pre><code class="language-php">// Entier aléatoire entre min et max
${'$'}de = rand(1, 6);
echo "Dé : " . ${'$'}de;

// Plus cryptographiquement sûr
${'$'}token = random_int(100000, 999999);
echo "Code : " . ${'$'}token;</code></pre>

<h3>Formatage</h3>
<pre><code class="language-php">${'$'}prix = 1234567.891;
echo number_format(${'$'}prix, 2, ',', ' ');
// 1 234 567,89

echo sprintf("Prix: %.2f €", ${'$'}prix);
// Prix: 1234567,89 €</code></pre>
            """.trimIndent()
        ),
        Lesson(7, 1, "Tableaux avancés",
            "Triez, filtrez et transformez vos tableaux avec les fonctions PHP.",
            """
<h2>Fonctions de tableaux</h2>
<p>PHP dispose de fonctions puissantes pour manipuler les tableaux sans écrire de boucles manuelles.</p>

<h3>Tri</h3>
<pre><code class="language-php">&lt;?php
${'$'}nombres = [3, 1, 4, 1, 5, 9, 2, 6];
sort(${'$'}nombres);       // Tri croissant (modifie le tableau)
rsort(${'$'}nombres);      // Tri décroissant

${'$'}notes = ["Alice" => 18, "Bob" => 15, "Charlie" => 17];
asort(${'$'}notes);        // Tri par valeur (garde les clés)
ksort(${'$'}notes);        // Tri par clé
?&gt;</code></pre>

<h3>Transformation</h3>
<pre><code class="language-php">${'$'}nums = [1, 2, 3, 4, 5];

// array_map : applique une fonction à chaque élément
${'$'}doubles = array_map(fn(${'$'}n) => ${'$'}n * 2, ${'$'}nums);
// [2, 4, 6, 8, 10]

// array_filter : garde les éléments qui passent le test
${'$'}pairs = array_filter(${'$'}nums, fn(${'$'}n) => ${'$'}n % 2 === 0);
// [2, 4]

// array_reduce : réduit à une seule valeur
${'$'}somme = array_reduce(${'$'}nums, fn(${'$'}acc, ${'$'}n) => ${'$'}acc + ${'$'}n, 0);
// 15</code></pre>

<div class="explain-box">
<strong>💡 array_map vs foreach</strong><br>
array_map est plus concis et expressif. Utilisez foreach quand vous avez besoin d'effets de bord (modifier des variables externes), et array_map pour les transformations pures.
</div>
            """.trimIndent()
        ),
        Lesson(8, 1, "Dates & Temps",
            "Gérez les dates avec les fonctions date() et la classe DateTime.",
            """
<h2>Dates & Temps en PHP</h2>
<p>PHP offre deux approches pour gérer les dates : les fonctions procédurales (<code>date()</code>, <code>time()</code>) et la classe orientée objet <code>DateTime</code>.</p>

<h3>Fonctions de base</h3>
<pre><code class="language-php">&lt;?php
// Timestamp Unix (secondes depuis 01/01/1970)
${'$'}now = time();
echo ${'$'}now;  // ex: 1735689600

// Formater la date actuelle
echo date("d/m/Y");         // 31/12/2024
echo date("H:i:s");         // 14:30:00
echo date("d/m/Y H:i:s");   // 31/12/2024 14:30:00
echo date("l, d F Y");      // Tuesday, 31 December 2024

// Formater un timestamp spécifique
echo date("d/m/Y", mktime(0, 0, 0, 7, 14, 1789)); // 14/07/1789
?&gt;</code></pre>

<h3>Classe DateTime (recommandée)</h3>
<pre><code class="language-php">${'$'}date = new DateTime();
echo ${'$'}date->format("d/m/Y"); // Date actuelle

${'$'}noel = new DateTime("2024-12-25");
${'$'}maintenant = new DateTime();

${'$'}diff = ${'$'}maintenant->diff(${'$'}noel);
echo ${'$'}diff->days . " jours depuis Noël";

// Ajouter du temps
${'$'}date->modify("+7 days");
echo ${'$'}date->format("d/m/Y");</code></pre>
            """.trimIndent()
        ),
        Lesson(9, 1, "Portée des variables",
            "Comprenez la portée locale, globale et les variables statiques.",
            """
<h2>Portée des variables</h2>
<p>En PHP, les variables ont une portée (scope) qui définit où elles sont accessibles. C'est différent de JavaScript ou Python !</p>

<h3>Portée locale vs globale</h3>
<pre><code class="language-php">&lt;?php
${'$'}global = "Je suis global";

function maFonction() {
    // ${'$'}global N'EST PAS accessible ici !
    echo ${'$'}global; // Erreur ou vide

    ${'$'}local = "Je suis local";
    echo ${'$'}local; // OK
}

maFonction();
// echo ${'$'}local; // Erreur — ${'$'}local n'existe pas ici
?&gt;</code></pre>

<h3>Le mot-clé global</h3>
<pre><code class="language-php">${'$'}compteur = 0;

function incrementer() {
    global ${'$'}compteur; // Déclare l'accès à la variable globale
    ${'$'}compteur++;
}

incrementer();
incrementer();
echo ${'$'}compteur; // 2</code></pre>

<h3>Variables statiques</h3>
<pre><code class="language-php">function compter() {
    static ${'$'}n = 0; // Initialisée UNE seule fois
    ${'$'}n++;
    echo ${'$'}n . "\n";
}

compter(); // 1
compter(); // 2
compter(); // 3</code></pre>

<div class="note-box">⚠️ L'usage de <strong>global</strong> est souvent un mauvais signe dans le code. Préférez passer les valeurs en paramètres de fonctions.</div>
            """.trimIndent()
        ),

        // ── MODULE 03 : Structures de Contrôle (index 10-14) ─────────────────
        Lesson(10, 2, "Conditions if / else / elseif",
            "Contrôlez le flux d'exécution selon des conditions booléennes.",
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
        Lesson(11, 2, "Switch & Match",
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
        Lesson(12, 2, "Boucles for & while",
            "Répétez des opérations avec for, while et do-while.",
            """
<h2>Boucles for & while</h2>
<p>PHP propose plusieurs structures de boucle. <strong>for</strong> est idéal quand on connaît le nombre d'itérations à l'avance. <strong>while</strong> continue tant qu'une condition est vraie.</p>

<h3>Boucle for</h3>
<pre><code class="language-php">for (${'$'}i = 1; ${'$'}i <= 5; ${'$'}i++) {
    echo "Itération n°" . ${'$'}i . "\n";
}

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
        Lesson(13, 2, "foreach sur tableaux",
            "Itérez sur des tableaux avec foreach — la boucle la plus utilisée en PHP.",
            """
<h2>foreach — La boucle PHP essentielle</h2>
<p><strong>foreach</strong> est conçu spécifiquement pour les tableaux. Il est plus lisible que for et gère automatiquement les tableaux associatifs.</p>

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
        Lesson(14, 2, "break & continue",
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
            "FACILE", 10,
            "<?php\nfor (\$i = 1; \$i <= 100; \$i++) {\n    // TODO: logique FizzBuzz ici\n    echo \$i . \"\\n\";\n}\n?>"
        ),
        Challenge(
            "Palindrome Checker",
            "Vérifiez si un mot est un palindrome sans utiliser strrev().",
            "MOYEN", 25,
            "<?php\nfunction isPalindrome(\$str) {\n    // TODO: sans utiliser strrev()\n    return false;\n}\n\necho isPalindrome(\"radar\") ? \"Oui\" : \"Non\";\n?>"
        ),
        Challenge(
            "Fibonacci Suite",
            "Générez les N premiers termes de la suite de Fibonacci avec récursivité.",
            "MOYEN", 30,
            "<?php\nfunction fibonacci(\$n) {\n    // TODO: implémentez la récursivité\n    return \$n;\n}\n\nfor (\$i = 0; \$i < 10; \$i++) {\n    echo fibonacci(\$i) . \" \";\n}\n?>"
        ),
        Challenge(
            "Trier sans sort()",
            "Implémentez le tri à bulles (bubble sort) manuellement.",
            "DIFFICILE", 50,
            "<?php\nfunction bubbleSort(\$arr) {\n    // TODO: implémentez le tri à bulles\n    return \$arr;\n}\n\n\$numbers = [64, 34, 25, 12, 22, 11, 90];\n\$sorted = bubbleSort(\$numbers);\necho implode(\", \", \$sorted);\n?>"
        ),
        Challenge(
            "Compteur de mots",
            "Comptez la fréquence de chaque mot dans une chaîne donnée.",
            "FACILE", 15,
            "<?php\n\$texte = \"le chat mange le poisson le chat\";\n// TODO: compter chaque mot\n?>"
        ),
        Challenge(
            "Mini calculatrice",
            "Parsez une expression arithmétique simple comme '3 + 5 * 2'.",
            "DIFFICILE", 60,
            "<?php\nfunction calculate(\$expression) {\n    // TODO: parser l'expression\n    return 0;\n}\n\necho calculate(\"3 + 5\"); // 8\n?>"
        )
    )
}
