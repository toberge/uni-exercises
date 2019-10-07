// @flow

const SHRUG = '===== ¯\\_(ツ)_/¯ =====';

let v1 = [1, 2, 3];
let v2 = [4, 5, 6];

console.error('2 + v1: [ 3, 4, 5 ]');
console.log(v1.map(v => v + 2));

console.error('2 * v1: [ 2, 4, 6 ]');
const timesTwo = v => v * 2;
console.log(v1.map(timesTwo));

console.error('mean of v1: 2');
const sum = (acc, v) => acc + v;
console.log(v1.reduce(sum) / v1.length);

console.error('v1 dot v2: 32');
console.log(v1.map<number[]>((v, i) => [v, v2[i]])
              .map<number>((pair) => pair[0] * pair[1])
              .reduce(sum));
console.log(v1.map((v, i) => v * v2[i]).reduce(sum));

console.error('sum of v1 + 2 * v2: 36');
console.log(v1.reduce(sum) + v2.map(timesTwo).reduce(sum));

console.error('v1 as string: v1[0] = 1, v1[1] = 2, v1[2] = 3');
console.log(v1.map<string>((v, i) => `v1[${i}] = ${v}, `)
              .reduce((v, acc) => acc.concat(v))
              .replace(/, $/, ''));


class Complex {
    real: number;
    imag: number;

    constructor(real: number, img: number) {
        this.real = real;
        this.imag = img;
    }
}

let v = [new Complex(2, 2), new Complex(1, 1)];

console.log();
console.log(SHRUG);
console.log();

console.error("v elements as strings: [ '2 + 2i', '1 + 1i' ]");
console.log(v.map(c => `${c.real} + ${c.imag}i`));

console.error('magnitude of v elements: [ 2.8284271247461903, 1.4142135623730951 ]');
console.log(v.map(c => Math.sqrt(c.imag*c.imag + c.real*c.real)));

console.error('sum of v: Complex { real: 3, imag: 3 }');
console.log(v.reduce((acc: Complex, c: Complex) => {
    acc.imag += c.imag;
    acc.real += c.real;
    return acc;
}, new Complex(0, 0)));


let students = [{ name: 'Ola', grade: 'A' }, { name: 'Kari', grade: 'C' }, { name: 'Knut', grade: 'C' }];

console.log();
console.log(SHRUG);
console.log();

console.error("students elements as strings: [ 'Ola got A', 'Kari got C', 'Knut got C' ]");
console.log(students.map(({name, grade}: {name: string, grade: string}) => `${name} got ${grade}`));

console.error('many got C: 2');
const gotC = ({grade}: {grade: string}) => grade === 'C';
const count = (acc: number, value: any) => ++acc;
console.log(students.filter(gotC).length);

console.error('entage of C grades: 0.6666666666666666');
console.log(students.filter(gotC).length / students.length/*reduce<number>((v: any, acc: number) => ++acc, 0)*/);
console.log(students.filter(gotC).length / students.reduce<number>(count, 0));

const withGrade = char => ({grade}) => char === grade;
console.error('anyone get A: Yes');
console.log(students.some(withGrade('A'))? 'Yes' : 'No');

console.error('anyone get F: No');
console.log(students.every(withGrade('F'))? 'Yes' : 'No');

