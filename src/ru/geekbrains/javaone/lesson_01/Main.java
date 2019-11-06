package ru.geekbrains.javaone.lesson_01;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static final int symbolsCountForWin = 4;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '.';

    private static void initField() {
        fieldSizeX = 5;
        fieldSizeY = 5;
        field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = DOT_EMPTY;
            }
        }
    }
    private static void showField() {
        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print("|");
            for (int j = 0; j < fieldSizeX; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println("-------");
    }
    private static void humanTurn() {
        int x;
        int y;
        do {
            System.out.printf("Введите координаты X и Y (от 1 до %d) через %s>>> ", fieldSizeX, "пробел");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isValidCell(x, y) || !isEmptyCell(x, y));
        field[y][x] = DOT_HUMAN;
    }

    private static boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }
    private static boolean isEmptyCell(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }
    private static void aiTurn() {
        int x;
        int y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field[y][x] = DOT_AI;
    }

    private static boolean checkWin(char c) {
        // проверим строки на выигрыш
        for (int y = 0; y < fieldSizeY; y++) {
            int count = 0; // счетчик количества нужных символов
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == c) { // [0][0] [0][1] [0][2]
                    count++;
                } else { // если встретили неверный символ, сбрасываем счетчик
                    count = 0;
                }

                if (count == symbolsCountForWin) {
                    return true;
                }
            }
        }

        // проверим столбцы на выигрыш
        for (int x = 0; x < fieldSizeX; x++) {
            int count = 0;
            for (int y = 0; y < fieldSizeY; y++) {
                if (field[y][x] == c) { // [0][0] [1][0] [2][0]
                    count++;
                } else {
                    count = 0;
                }

                if (count == symbolsCountForWin) {
                    return true;
                }
            }
        }

        // проверяем диагонали
        for (int y = 0; y < fieldSizeY; y++) { // диагонали будем проверять начиная с каждой строки
            for (int x = 0; x < fieldSizeX; x++) { // начальная позиция в строке, откуда ищем диагонали
                int countLeft = 0;
                int countRight = 0;
                for (int stepY = y; stepY < fieldSizeY; stepY++) { // будем шагать вниз до последней строки
                    int offset = stepY - y; // смещение для колонки
                    int rightX = x + offset;
                    if (rightX < fieldSizeX) { // проверить диагональ вправо можно только если x не вышел за поле
                        if (field[stepY][rightX] == c) {
                            countRight++;
                        } else {
                            countRight = 0;
                        }

                        if (countRight == symbolsCountForWin) {
                            return true;
                        }
                    }

                    int leftX = x - offset;
                    if (leftX >= 0) { // проверить диагональ влево можно только если x не вышел за поле
                        if (field[stepY][leftX] == c) {
                            countLeft++;
                        } else {
                            countLeft = 0;
                        }

                        if (countLeft == symbolsCountForWin) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
    private static boolean isDraw() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == DOT_EMPTY)
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        initField();
        showField();
        while (true) {
            humanTurn();
            showField();
            if (checkWin(DOT_HUMAN)) {
                System.out.println("Human win!");
                break;
            }
            if (isDraw()) {
                System.out.println("Draw!");
                break;
            }
            aiTurn();
            showField();
            if (checkWin(DOT_AI)) {
                System.out.println("Computer win!");
                break;
            }
            if (isDraw()) {
                System.out.println("Draw!");
                break;
            }
        }
    }
/*
|.|.|.|.|.|
|X|.|.|.|.|
|O|X|.|O|.|
|.|.|X|.|O|
|.|.|.|X|.|
-------
Human win!
*/
}




