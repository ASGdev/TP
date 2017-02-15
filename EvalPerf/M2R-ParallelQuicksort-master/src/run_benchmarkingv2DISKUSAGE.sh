
OUTPUT_DIRECTORY=data/`hostname`_`date +%F`
mkdir -p $OUTPUT_DIRECTORY
OUTPUT_FILELE=$OUTPUT_DIRECTORY/measurementsLE_`date +%R`.txt
OUTPUT_FILEHE=$OUTPUT_DIRECTORY/measurementsHE_`date +%R`.txt

touch $OUTPUT_FILELE
for i in 10000 10000 10000; do
    for rep in `seq 1 5`; do
        echo "Size: $i" >> $OUTPUT_FILELE;
        ./parallelQuicksort $i >> $OUTPUT_FILELE;
    done ;
done

touch $OUTPUT_FILEHE
for i in 10000000 10000000 10000000; do
    for rep in `seq 1 5`; do
        echo "Size: $i" >> $OUTPUT_FILEHE;
        ./parallelQuicksort $i >> $OUTPUT_FILEHE;
    done ;
done
