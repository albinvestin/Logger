package com.example.logger;

import java.util.LinkedList;

public class StringList {
    private String _list = "";
    private int _rows = 0;
    private LinkedList<Integer> _rowStartIndex = new LinkedList<Integer>();
    private LinkedList<Integer> _rowEndIndex = new LinkedList<Integer>();

    public String addLine(String line) {
        int newStartIndex = 0;
        if (_rows > 0) {
            newStartIndex = _rowEndIndex.getLast()+1;
        }
        _rowStartIndex.add(newStartIndex);
        _rowEndIndex.add(newStartIndex+line.length());

        if (_rows == 0) {
            _list = line;
        } else {
            _list += "\n" + line;
        }
        _rows += 1;
        return _list;
    }

    public String deleteLastRow() {
        if (_rows == 0) {
            _list = "";
        } else {
            if (_rows > 1)
                _list = _list.substring(0,_rowStartIndex.getLast()-1);
            else
                _list = _list.substring(0,_rowStartIndex.getLast());
            _rowStartIndex.removeLast();
            _rowEndIndex.removeLast();
            _rows -= 1;
        }
        return _list;
    }

    public void loadFromString(String list) {
        String[] lines = list.split("\n");
        for (String line: lines) {
            addLine(line);
        }
    }

    public String GetString() {
        return _list;
    }
}
