#!/bin/bash
gnuplot << EOF
set pointsize 0.5
set pm3d map
set terminal jpeg
set output "$1.jpg"
set xrange [ -4 : 4 ]
set yrange [ -4 : 4 ]
splot '$1' with pm3d
EOF

