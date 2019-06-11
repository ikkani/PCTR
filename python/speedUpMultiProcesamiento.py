from multiprocessing import *;
import time;

def f(variable, p1):
	for i in xrange(0,100000):
		variable += 1
	p1[0].send(variable)

def f2(variable):
	for i in xrange(0,800000):
		variable += 1
	return variable

if __name__ == '__main__':
	p1 = Pipe(True)
	p2 = Pipe(True)
	variable = 0
	tiempo1 = time.time()
	p = Process(target=f, args=(variable, p1))
	p2 = Process(target=f, args=(variable, p1))
	p3 = Process(target=f, args=(variable, p1))
	p4 = Process(target=f, args=(variable, p1))
	p5 = Process(target=f, args=(variable, p1))
	p6 = Process(target=f, args=(variable, p1))
	p7 = Process(target=f, args=(variable, p1))
	p8 = Process(target=f, args=(variable, p1))
	p.start()
	p2.start()
	p3.start()
	p4.start()
	p5.start()
	p6.start()
	p7.start()
	p8.start()
	p.join()
	p2.join()
	p3.join()
	p4.join()
	p5.join()
	p6.join()
	p7.join()
	p8.join()
	variable += p1[1].recv();
	variable += p1[1].recv();
	variable += p1[1].recv();
	variable += p1[1].recv();
	variable += p1[1].recv();
	variable += p1[1].recv();
	variable += p1[1].recv();
	variable += p1[1].recv();
	print (variable)
	tiempoParalelo = time.time() - tiempo1
	print ("Tiempo con 8 procesos: " + str(tiempoParalelo) + " segundos")
	
	variable = 0
	tiempo1 = time.time()
	variable = f2(variable)
	print (variable)
	tiempoSecuencial = time.time() - tiempo1
	print ("Tiempo secuencial: " + str(tiempoSecuencial) + " segundos")

	print ("SpeedUp: " + str(tiempoSecuencial/tiempoParalelo))
	