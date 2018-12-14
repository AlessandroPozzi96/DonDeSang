package com.henallux.dondesang.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henallux.dondesang.R;

import java.util.ArrayList;
import java.util.Arrays;

public class FaqFragment extends Fragment {
    private View view;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_faq, container, false);
        linearLayout = view.findViewById(R.id.layout_faq);
        genererFaq(getQuestionsReponses());

        return view;
    }


    public ArrayList<String> getQuestionsReponses() {
        ArrayList<String> questionsReponses = new ArrayList<String>(
                Arrays.asList(
                        "Pourquoi faut-il chaque fois remplir un questionnaire médical ?", "Grâce au questionnaire et aux questions posées oralement par le personnel qualifié, il est possible de juger si l'état de santé actuel du donneur lui permet de donner son sang. Cela aussi bien dans l'intérêt du donneur lui-même que dans celui du receveur. C'est pourquoi le questionnaire comporte aussi des questions sur une éventuelle maladie récente, une opération, sur la possibilité de comportements à risque, etc.\n" +
                                "\n" +
                                "Des réponses correctes, précises et honnêtes à toutes ces questions sont indispensables pour la sécurité transfusionnelle.",
                        "Quelle est la fréquence des dons ?", "Toute personne en bonne santé peut donner du sang quatre fois par an, avec un intervalle de deux mois minimum entre chaque don de sang. \n" +
                                "Pour les dons de plasma, le délai à respecter est de deux semaines, avec un maximum de quinze litres par an. \n" +
                                "Pour les dons de plaquettes, il faut un intervalle de 2 semaines, et un maximum de 24 par an. \n" +
                                "Le délai à respecter entre un don de sang et un don de plasma/plaquettes est de 2 semaines.",
                        "Quelle quantité de sang donne-t-on ?", "La quantité varie en fonction du donneur, sans jamais dépasser 500 millilitres. Le sang est récolté dans une poche contenant un liquide anticoagulant et de conservation.\n" +
                                "\n" +
                                "Les poches utilisées, en plastique, sont du matériel à usage unique, y compris l'aiguille, utilisé donc une seule fois, et détruit après le don par incinération. Tout risque de contamination est donc exclu d'office.",
                        "Doit-on être à jeûn ?", "Certainement pas. Une légère collation est même souhaitable. Par contre, il faut s'abstenir de tout repas copieux afin de ne pas surcharger le sang en graisses.",
                        "Ce qui est prélevé, n'est-ce pas trop ?", "Un adulte a entre 4 et 6 litres de sang selon son poids. Le prélèvement adapté au poids du donneur ne présente aucun inconvénient pour un adulte pesant au moins 50 kilos, et la quantité de sang prélevée est remplacée par l'organisme de manière à la compenser parfaitement.",
                        "Qu'est-ce que l'autotransfusion ?", "Il s'agit de donner son sang pour soi-même, en prévision d'une opération programmée. Dans ce cas, veuillez en parler au médecin prescripteur (chirurgien, anesthésiste) ; si l'autotransfusion est une option éventuelle pour vous, il vous informera de la façon de procéder. Le médecin du Centre de Transfusion reste néanmoins seul juge de l'aptitude ou non du futur opéré à prédonner avant son intervention. La quantité de sang pouvant être donnée est cependant limitée. Si elle ne suffit pas lors de l'intervention, le médecin devra malgré tout recourir au sang d'un autre donneur.\n" +
                                "\n" +
                                "Depuis quelques années, l'autotransfusion a perdu de son engouement par suite d'une grande sécurité transfusionnelle et par les progrès des techniques chirurgicales permettant la diminution des besoins transfusionnels dans bon nombre d'interventions. ",
                        "Quelles sont les analyses pratiquées ?", "Tout d'abord, une série d'analyses qui concernent la recherche de maladies transmissibles par transfusion : hépatite B, hépatite C, SIDA et syphilis. S'y ajoutent la détermination du groupe sanguin, la numération des globules rouges, des globules blancs et des plaquettes sanguines.\n" +
                                "\n" +
                                "La recherche d'anticorps antimalariques est réalisée chez toute personne ayant séjourné en zone impaludée, après la mise en quarantaine de 6 mois.",
                        "Suis-je prévenu si une analyse n'est pas bonne ?", "Lorsque les résultats sont normaux, il n'y a pas de notification au donneur. Par contre, en cas d'anomalie, le donneur est prévenu par téléphone ou par courrier. Dans certains cas plus particuliers, le donneur peut éventuellement être convoqué par le médecin du Centre de Transfusion pour lui en parler.",
                        "Est-ce que cela fait mal quand je donne du sang ?", "Au moment de la piqûre, on sent à peine l'aiguille entrer dans la veine. Quant à la prise de sang elle-même, elle est tout à fait indolore. ",
                        "Combien de temps faut-il au corps humain pour remplacer le sang prélevé ?", "La perte de liquide est compensée en un jour. Il n'en faut pas beaucoup plus pour les globules blancs. Pour les globules rouges, la reconstitution est plus lente. Au bout de 6 à 8 semaines, le corps aura remplacé tout le sang donné. Mais on ne ressent rien de particulier pendant cette période. ",
                        "Est-ce que le don influence la pression sanguine ?", "La pression sanguine ou tension artérielle baisse très légèrement après un prélèvement, mais elle remonte et se stabilise vite.\n" +
                                "\n" +
                                "Le don de sang n'est donc pas un traitement de l'hypertension.",
                        "Est-ce que le prélèvement abime les veines ?", "Non, tout au plus il peut y avoir une petite cicatrice à l'endroit où l'on pique habituellement.",
                        "Que devient le sang que je donne en cas d'anomalie ?", "Si les analyses pratiquées révèlent un résultat anormal, le sang est détruit par incinération.",
                        "Y a-t-il des règles spéciales pour les sportifs ?", "Après un don, la pratique du sport est autorisée de façon modérée. Un délai de douze heures est conseillé pour pratiquer les sports suivants :\n" +
                                "\n" +
                                "cyclisme, VTT, natation\n" +
                                "sports automobiles, moto de compétition\n" +
                                "tous les sports \" violents \", tels le judo, la boxe, la lutte, le karaté, rugby, football américain, hockey sur glace...\n" +
                                "Il n'est pas conseillé de donner dans les 24 heures précédant ou suivant une compétition importante, ou précédant ou suivant un des sports suivants : alpinisme, deltaplane, plongée sous-marine, spéléologie.",
                        "Y a-t-il des règles spéciales pour les automobilistes ?", "Pas particulièrement. Le temps de repos pris lors de la collation suffit pour pouvoir reprendre le volant, pour autant que l'on se sente bien.",
                        "Les produits sanguins, de quoi s'agit-il ?", "Le sang est constitué de cellules sanguines ou éléments figurés et de plasma. Pour la transfusion, on n'utilise plus le sang complet.\n" +
                                "\n" +
                                "A partir des éléments figurés, on confectionne des concentrés de globules rouges (érythrocytes) et de plaquettes sanguines (thrombocytes).\n" +
                                "\n" +
                                "Quant au plasma, on en tire des produits tels que des facteurs de coagulation, des immunoglobulines, de l'albumine.\n" +
                                "\n" +
                                "Les patients transfusés ne reçoivent que les composants du sang qui leur sont effectivement nécessaires. Chaque don de sang est ainsi utilisé de manière optimale, et un seul don peut aider plusieurs malades ou blessés.\n" +
                                "\n" +
                                "Les plaquettes sanguines ne se conservent que pendant 5 jours\n" +
                                "Les globules rouges, eux, se conservent 42 jours\n" +
                                "Les produits dérivés du plasma, comme l'albumine, peuvent en revanche être conservés pendant 2 ans ou plus.",
                        "Existe-t-il du sang artificiel ?", "Le sang est un organe liquide dont la constitution est si complexe qu'il ne sera sans doute jamais possible d'en fabriquer synthétiquement un qui pourra couvrir toutes les indications de la transfusion. On ne pourra donc pas renoncer aux dons de sang, si l'on veut continuer à traiter les malades et les blessés qui en ont besoin.\n" +
                                "\n" +
                                "Les méthodes de génie génétique permettent toutefois aujourd'hui de produire artificiellement certains dérivés du plasma, tels les facteurs de coagulation, nécessaires aux hémophiles.",
                        "Les dons de sang sont-ils suffisants en Belgique ?", "La Belgique est un pays où le sang est disponible en suffisance. On constate toutefois que le nombre des donneurs diminue régulièrement d'année en année. Cela est dû, en particulier, au fait que les critères de sélection médicale sont devenus de plus en plus stricts.\n" +
                                "\n" +
                                "Pour garder notre autosuffisance, le Service du Sang doit pouvoir compter sur l'assiduité des donneurs existants, mais aussi sur un renouvellement permanent.\n" +
                                "\n" +
                                "Les groupes sanguins rares sont-ils plus précieux ?\n" +
                                "\n" +
                                "Groupe  O : 44 %\n" +
                                "Groupe  A : 45 %\n" +
                                "Groupe  B :   8 %\n" +
                                "Groupe AB :  3 %\n" +
                                "La proportion des besoins en concentrés érythrocytaires est quasi identique. Mais, dans certains cas, rares, par exemple l'exsanguino-transfusion des nouveaux-nés, certains groupes sanguins sont plus demandés.\n" +
                                "\n" +
                                "De même, en cas d'extrême urgence, le groupe sanguin O Rh (D) négatif est utilisé en tant que groupe universel ; à ce titre, il est très important. A l'inverse, les besoins en globules des groupes sanguins B et AB restent limités ; mais leur plasma est précieux et sauve des vies.\n" +
                                "\n" +
                                "En tout état de cause, tous les dons de sang de n'importe quel groupe sont indispensables, même si c'est à des degrés divers.",
                        "Quand utilise-t-on le sang ?", "On a surtout besoin de sang pour les personnes qui en ont perdu beaucoup, en cas d'accident ou d'opération grave.\n" +
                                "\n" +
                                "Il faut également disposer de produits sanguins pour traiter les maladies qui affectent la fabrication du sang, par exemple dans le cas des leucémies.",
                        "Est-ce que la Belgique vend du sang à l'étranger ?", "Non. Il n'y a normalement pas d'excédents qui le permettraient.\n" +
                                "\n" +
                                "Par ailleurs, la loi interdit tout profit en relation avec le sang. Néanmoins, en cas de grands besoins à l'étranger (tremblement de terre par exemple), la Belgique peut être sollicitée pour une aide ponctuelle.",
                        "Pourquoi le sang n'est-il pas distribué gratuitement aux hôpitaux ?", "Le don de sang n'est pas rémunéré ; et pourtant, il n'est pas délivré gratuitement aux hôpitaux. Cela peut surprendre au premier abord.\n" +
                                "\n" +
                                "En réalité, le prix des produits sanguins est fixé par la loi, et couvre les différentes opérations nécessaires avant leur utilisation : prélèvement, préparation, analyses, stockage et distribution ; tout cela nécessite un personnel qualifié et du matériel performant.",
                        "Puis-je contracter une maladie en donnant du sang ?", "NON. D'une part, avant chaque don, on vérifie soigneusement l'aptitude du donneur à faire don de son sang. D'autre part, il n'y a aucun risque de contamination puisqu'on utilise pour chaque don un matériel stérile et à usage unique, détruit par incinération après utilisation. Il en va de même pour les dons de plasma et de plaquettes.",
                        "Les produits sanguins sont-ils sûrs ?", "Le Service du Sang fait tout ce qui est possible pour garantir une sécurité optimale des transfusions sanguines. L'interrogatoire des donneurs et les analyses systématiques sont des mesures qui réduisent au maximum les risques.\n" +
                                "\n" +
                                "La mise en oeuvre de divers procédés d'élimination d'agents pathogènes éventuels durant la préparation des dérivés stables issus du plasma diminue encore le risque pour ces produits.\n" +
                                "\n" +
                                "Un système de qualité est mis en place, afin de maîtriser tous les procédés en relation avec les activités du Service du Sang.",
                        "Où et quand peut-on donner du sang ?", "En collecte itinérante : tous les lieux de prélèvement et leurs horaires sont rassemblés en une seule rubrique. Cet agenda est remis à jour chaque mois. Il vous permet ainsi de situer facilement le lieu le plus aisé pour vous, pour faire don de votre sang.\n" +
                                "\n" +
                                "Dans les Centres fixes : les horaires, adresses et numéros de téléphone des Centres fixes de Transfusion sont repris dans la rubrique l'Agenda des Collectes\n" +
                                "\n" +
                                "Un numéro de téléphone vert est également à votre disposition pour tout renseignement : 0800 92 245"

                )
        );

        return questionsReponses;
    }

    public void genererFaq(ArrayList<String> questionsReponses) {
        TextView ligne;
        for (int i = 0; i < questionsReponses.size(); i++) {
            ligne = new TextView(getActivity());
            ligne.setText(questionsReponses.get(i));
            ligne.setPadding(30, 20, 20, 30);
            //Quand c'est une question
            if (i % 2 == 0) {
                ligne.setTextSize(18);
                ligne.setGravity(Gravity.CENTER);
                ligne.setTypeface(null, Typeface.BOLD);
            }
            //Quand c'est une réponse
            else
            {
                ligne.setTextSize(16);
                ligne.setTypeface(null, Typeface.ITALIC);
            }
            linearLayout.addView(ligne);
        }
    }
}
