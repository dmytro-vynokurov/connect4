package com.dvynokurov.service;

import com.dvynokurov.model.Cell;
import com.dvynokurov.model.Column;
import com.dvynokurov.model.Grid;
import com.dvynokurov.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.BitSet;

@Service
public class GridSerializationService {
    private static final int BITS_PER_CELL = 2;
    private static final int SIZE_OF_INT_IN_BITS = 32;
    private static final int NUMBER_OF_DIMENSIONS = 2;

    @Autowired
    GridGenerationService gridGenerationService;


    public Grid decode(byte[] bytes) {
        BitSet bitSet = BitSet.valueOf(bytes);
        Grid grid = parseHeader(bitSet);
        parseBody(bitSet, grid);
        return grid;
    }

    private void parseBody(BitSet bitSet, Grid grid) {
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                int bitIndex = getHeaderSize() + i * getEncodedColumnSize(grid) + BITS_PER_CELL * j;
                boolean firstBit = bitSet.get(bitIndex);
                boolean secondBit = bitSet.get(bitIndex + 1);
                Player player = parsePlayer(firstBit, secondBit);
                Cell cell = grid.getColumns().get(i).getCells().get(j);
                cell.setOwner(player);
            }
        }
    }

    private Player parsePlayer(boolean firstBit, boolean secondBit) {
        if(firstBit) {
            return Player.FIRST;
        }else {
            if (secondBit) {
                return Player.SECOND;
            } else {
                return null;
            }
        }
    }

    private Grid parseHeader(BitSet bitSet) {
        long header = bitSet.toLongArray()[0];
        int width = (int)(header >> 32);
        int height = (int)(header & 0x00000000ffffffffL);
        return gridGenerationService.generateGrid(width, height);
    }

    public byte[] encode(Grid grid) {
        int encodedGridSize = getEncodedGridSize(grid);
        BitSet bitSet = new BitSet(encodedGridSize);
        writeGridHeader(grid, bitSet);
        writeGridBody(grid, bitSet);
        return Arrays.copyOf(bitSet.toByteArray(), (encodedGridSize + 7) / 8); //hack used not to loose first bits from BitSet if they are 0
    }

    private void writeGridHeader(Grid grid, BitSet bitSet) {
        long header = ((long) grid.getWidth() << 32) + grid.getHeight();
        BitSet headerBitSet = BitSet.valueOf(new long[]{header});
        bitSet.or(headerBitSet);
    }

    private void writeGridBody(Grid grid, BitSet bitSet) {
        for (int i = 0; i < grid.getColumns().size(); i++) {
            Column column = grid.getColumns().get(i);
            for (int j = 0; j < column.getCells().size(); j++) {
                Cell cell = column.getCells().get(j);
                int bitIndex = getHeaderSize() + i * getEncodedColumnSize(grid) + BITS_PER_CELL * j;
                bitSet.set(bitIndex, cell.getOwner()== Player.FIRST);
                bitSet.set(bitIndex + 1, cell.getOwner()==Player.SECOND);
            }
        }
    }

    private int getEncodedGridSize(Grid grid) {
        return grid.getWidth() * getEncodedColumnSize(grid) + getHeaderSize();
    }

    private int getHeaderSize() {
        return SIZE_OF_INT_IN_BITS * NUMBER_OF_DIMENSIONS;
    }

    private int getEncodedColumnSize(Grid grid) {
        return grid.getHeight() * BITS_PER_CELL;
    }

}
