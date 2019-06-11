#include <thread>
#include <iostream>
#include <vector>
#include <atomic>
#include <mutex>
#include <chrono>
#include <ctime>
#include <iomanip>

using namespace std;

int n;
atomic<int> nAtomica;
mutex m;
int accesosRecurso;

int main(int argc, char const *argv[])
{
	std::vector<thread> hilos;
	accesosRecurso = 10;
	int nHilos = 10;
	n = 0;
	nAtomica.store(0);

	cout << "Total de accesos al recurso" <<  setw(30) <<"Tiempo con mutex" << setw(50) << "Tiempo con atomic" << endl;

	for (int iteracion = 0; iteracion < 6; iteracion++){
		accesosRecurso *= 10;
		cout << accesosRecurso << setw(50);

		//Mutex
		auto start = chrono::system_clock::now();
		for (int hilo = 0; hilo < nHilos; ++hilo){
			hilos.push_back(thread([](){
				for (int i = 0; i < accesosRecurso; i++){
				m.lock();
					++n;
				m.unlock();
				}
			}));
		}

		for (int i = 0; i < nHilos; ++i)
			hilos[i].join();
		
		auto end = chrono::system_clock::now();
		chrono::duration<float, std::milli> duration = end-start;
		cout << duration.count()<< " ms" <<setw(50);
		//Fin mutex
		n = 0;
		hilos.clear();

		start = chrono::system_clock::now();
		for (int hilo = 0; hilo < nHilos; ++hilo){
			hilos.push_back(thread([](){
				for (int i = 0; i < accesosRecurso; ++i)
					++nAtomica;
			}));
		}
		for (int i = 0; i < nHilos; ++i)
			hilos[i].join();
		end = chrono::system_clock::now();

		duration = end-start;
		cout << duration.count()<< " ms"<<endl;
		nAtomica.store(0);
		hilos.clear();
	}

	return 0;
}