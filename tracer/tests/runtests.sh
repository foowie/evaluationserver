#!/bin/bash

tracer="../dist/Release/GNU-Linux-x86/tracer"
testsDir="tests/"
chmod +x $tracer

# test: (script_name, expected_result)
tests=("printf" "scanf" "fopen" "memory" "memory_malloc" "sigsegv" "output" "time")
results=("AC"   "AC"    "RF"    "ML"     "ML"            "RE"      "OL"     "TL")

echo "Compiling ..."

for item in ${tests[*]}
do
    g++ $testsDir$item".cpp" -o $testsDir$item
 #   chmod +x $testsDir$item
done

echo "Processing ${#tests[*]} tests"

for index in ${!tests[*]}
do
    printf "Test [%s] with expected result %s\n" ${tests[$index]} ${results[$index]}
    #     tracer           test file                       infile outfile time memory  output log file
    torun=$tracer" \""`pwd`"/"$testsDir${tests[$index]}"\" input  output  1000 5000000 1000"
    output=$(eval $torun)

    printf "Code: %s Time: %sms Memory: %sb - " $output

    code=$(echo $output | awk '{print $1}')
    if [ ${results[$index]} = $code ] ; then
        printf "OK\n\n" $code
    else
        printf "FAIL (%s)\n" $code
        exit 1
    fi

done

rm output
#rm log.txt
