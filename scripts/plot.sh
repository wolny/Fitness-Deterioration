#!/bin/bash
gnuplot << EOF
set terminal jpeg
set output "$1.jpg"
set xrange [ -4 : 4 ]
set yrange [ -4 : 4 ]
set xlabel 'x'
set ylabel 'y'
plot '$1'
EOF

