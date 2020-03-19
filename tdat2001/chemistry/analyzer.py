import csv
import periodictable
import math
# pt is NOT from the scipy package.

### HELLO THERE
### THIS .py file is DROPPED
### Jupyter FTW

"""
Chemistry Assigment

The aim of the assignment is to validate the published densities’
data of elements in the periodic table by using cross checking them with atomic data.
Specifically, you are required to write a program that reads the atomic size of each element and its atomic weight,
calculate the density of a single atom (atomic weight/ atomic volume)
and compare the results with the tabulated density of the element (see second link).
Note that each element has a theoretical and an empirical size: Your calculation should be based on both.

You are further, required to calculate the distances between the atoms of a given element
that would result in an accurate estimation of the tabulated densities.
The results that you obtain should be compared with the published data
for number of atoms per unit volume (see the second link).

For those of you who want to learn more, you can attempt to visualise your results in a meaningful way.


Note: you are encouraged to work in groups but the submission is individual.


Atomic radii data:
https://en.wikipedia.org/wiki/Atomic_radii_of_the_elements_(data_page)

Density data with number of atoms per volume:
https://en.wikipedia.org/wiki/Talk%3AList_of_elements_by_density/Numeric_densities
"""

elements = {}

# with open('radii.csv') as file:
#     # csv.DictReader(file) is a possibility
#     reader = csv.reader(file)
#     for row in reader:
#         print(row)
#         elements.append({
#             'number': row[0],
#             'symbol': row[1],
#             'name': row[2],
#             'empirical': row[3],
#             'calculated': row[3]
#         })
#         print(elements[-1])

# Using DictReader and manipulating the file's header names
with open('radii.csv') as file:
    reader = csv.DictReader(file)
    count = 0
    for row in reader:
        count += 1
        #print(row)
        #if not 'no data' in [row['empirical'], row['calculated']]:
        s = row['symbol']
        if s in elements:
            print(s)
        element = {
            'number': int(row['number']),
            'symbol': s,
            'name': row['name'],
            'empirical_radius': int(row['empirical']) *10**10 if 'no data' != row['empirical'] else None,
            'calculated_radius': int(row['calculated']) *10**10 if 'no data' != row['calculated'] else None
        }
        element['mass'] = periodictable.elements[element['number']].mass
        elements[s] = element
        if not element['empirical_radius'] and not element['calculated_radius']:
            print('uhhhhhhhhhh', s, 'has bad')
        #else:
        #    print('forkastet', row['symbol'], row['empirical'], row['calculated'])

    print('From a total of', count, 'elements we keep', len(elements))

with open('density.csv') as file:
    reader = csv.DictReader(file)
    for row in reader:
        s = row['symbol']
        if s not in elements:
            print('skipping', s)
            continue
        elements[s]['known_density'] = float(row['density'].split()[0])
        elements[s]['known_count'] = float(row['number of atoms per volume unit'].split()[0])
        print(elements[s]['known_density'], elements[s]['known_count'])

# calculate density
# 3 / 4π r²

exp = 10**10
def density(radius, mass, count):
    #return (3*mass) / (4*math.pi * (radius * exp)**3) # pico to centi --> -12 to -2 and cubed
    return count * 10**21 *  (3*mass*10**(-3)) / (2.06 * 10**23 * 4*math.pi * (radius * 10**(-6))**3)

for _, element in elements.items():
    if element.get('calculated_radius'):
        # atomic mass is in g/cm³
        atomic_density = 3*element['mass'] / (4*math.pi * (element['calculated_radius'])**3)
        calculated_densty = atomic_density * element['known_count'] * 10**21
        print(element['name'], calculated_densty, element['known_density'])

print(elements['H']['calculated_radius'])

for _, element in elements.items():
    if element.get('empirical_radius'):
        element['empirical_density'] = density(element['empirical_radius'], element['mass'], element['known_count'])
        print(element['empirical_density'], element['known_density'])
    if element.get('calculated_radius'):
        element['calculated_density'] = density(element['calculated_radius'], element['mass'], element['known_count'])
        print(element['calculated_density'])

# calculate the weird stuff
