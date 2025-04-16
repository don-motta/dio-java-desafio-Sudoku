import com.model.Board;
import com.model.Space;
import com.util.BoardTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Sudoku {
    private final static Scanner sc = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {

        //mapeia o argumento de entrada com as posições pré definidas do jogo
        final var positions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));

        var option = -1;

        while (true) {
            System.out.println("Escolha uma das opções: ");
            System.out.println("""
                    1 - Iniciar novo jogo
                    2 - Colocar um valor
                    3 - Remover um valor
                    4 - Visuallizar o jogo atual
                    5 - Verificar status do jogo
                    6 - Limpar jogo
                    7 - Finalizar jogo
                    8 - Sair""");
            option = sc.nextInt();

            switch (option) {
                case 1 -> startGame(positions);
                case 2 -> inputValue();
                case 3 -> removeValue();
                case 4 -> showCurrentGame();
                case 5 -> checkStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("Um jogo já está em andamento. Por favor, finalize-o antes de iniciar um novo.");
            return;
        }
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j =0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var isFixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, isFixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("Jogo iniciado!");
    }

    private static void inputValue() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }

        System.out.println("Informe a coluna onde deseja inserir um valor: ");
        var col = runUntilGetValidInput(0,8);
        System.out.println("Informe a linha onde deseja inserir um valor: ");
        var row = runUntilGetValidInput(0,8);
        System.out.printf("Informe o valor que deseja inserir na posição (%s, %s):\n", col, row);
        var value = runUntilGetValidInput(1,9);
        if (!board.changeValue(col, row, value)) {
            System.out.printf("A posição (%s, %s) já está preenchida com um valor fixo.\n", col, row);
        }
    }

    private static void removeValue() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }
        System.out.println("Informe a coluna onde deseja remover um valor: ");
        var col = runUntilGetValidInput(0,8);
        System.out.println("Informe a linha onde deseja remover um valor: ");
        var row = runUntilGetValidInput(0,8);
        if (!board.clearValue(col, row)) {
            System.out.printf("A posição (%s, %s) já está preenchida com um valor fixo.\n", col, row);
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }
        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++){
            for (var col : board.getBoard()){
                args[argPos++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
            System.out.println("Seu jogo se encontra da seguinte forma: ");
            System.out.printf((BOARD_TEMPLATE) + "%n", args);
        }
    }

    private static void checkStatus() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }
        System.out.printf("O status do jogo no momento é: %s\n", board.getStatus().getLabel());
        if (board.hasErrors()){
            System.out.println("Existem erros no jogo.");
        } else {
            System.out.println("Não existem erros no jogo.");
        }
    }

    private static void clearGame() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }
        System.out.println("Tem certeza que deseja limpar o jogo? (S/N)");
        var option = sc.next();
        while (!option.equalsIgnoreCase("S") && !option.equalsIgnoreCase("N")) {
            System.out.println("Opção inválida. Tente novamente.");
            option = sc.next();
        }
        if(option.equalsIgnoreCase("S")) {
            board.reset();
            System.out.println("Jogo limpo com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private static void finishGame() {
        if(isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado.");
            return;
        }
        if (board.gameIsFinished()) {
            System.out.println("Parabéns! Você completou o jogo com sucesso!");
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()) {
            System.out.println("Seu jogo contém erros. Verifique o board.");
        } else {
            System.out.println("Você precisa preencher todos os espaços.");
        }
    }







    private static int runUntilGetValidInput(final int min, final int max) {
        try {
            var current = sc.nextInt();
            while (current < min || current > max) {
                System.out.printf("Valor inválido. Tente novamente informando um numero entre %s a %s:\n", min, max);
                current = sc.nextInt();
            }
            return current;
        } catch (Exception e) {
            System.out.println("Deverá ser digitado um valor numerico.");
            sc.nextLine();
            return runUntilGetValidInput(min, max);
        }
    }
}
