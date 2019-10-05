// @flow

// goes on about removing parenthesis
const isOdd = value => value % 2 === 1;
// them oneliners look good
const isOddAndLong = value => {
    return value % 2 === 1;
};
const isOddAndLonger = (value) => {
    return value % 2 === 1;
};

const please = thing => (
    console.log(thing
        ? thing
        : 'none')
);

console.log(isOdd(69), isOdd(42));


/*==== functional algorithms ====*/

let nums: number[] = [2, 3];
// le olde way
// for (let num of nums) {
//     numsPlus2.push(num + 2);
// }
let numsPlus2: number[] = nums.map(a => a+2);
console.log(numsPlus2);

// second param is starting value for accumulator
let sum = nums.reduce<number>((num, acc) => num + acc, 0);
// first acc = 0
// then  acc += num
console.log('sum is', sum);

// only keep those that pass the test
console.log(nums.filter(num => num > 2));
// can chain
console.log(nums.filter(num => num > 2).map(num => num * 2));

console.log('some odd?', nums.some(isOdd));
console.log('all odd?', nums.every(isOdd));

