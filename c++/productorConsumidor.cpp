#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <vector>

using namespace std;

struct Monitor
{
	mutex lock;
	condition_variable vacio, lleno;
	vector<int> buffer;


	void producir(){

		unique_lock<mutex> cerrojo(lock);

		lleno.wait(cerrojo, [this](){return buffer.size() != 10;});

		buffer.push_back(1);
		cout <<  "Relleno uno, quedan " << buffer.size() << endl;

		vacio.notify_one();

	}

	void consumir(){

		unique_lock<mutex> cerrojo(lock);
		while (buffer.size() == 0){
			lleno.notify_all();
			vacio.wait(cerrojo);
		}

		buffer.pop_back();
		cout << "Consumo uno, quedan " << buffer.size() << endl; 

	}
	
};


int main(int argc, char const *argv[])
{
	Monitor m;

	std::vector<thread> productores;
	std::vector<thread> consumidores;


	for (int i = 0; i < 2; ++i)
	{
		productores.push_back(thread([&m](){
			while(true){
				m.producir();
			}
		}));
	}

	for (int i = 0; i < 10; ++i)
	{
		consumidores.push_back(thread([&m](){
			while(true){
				m.consumir();
			}
		}));
	}

	for (int i = 0; i < 2; ++i)
	{
		productores[i].join();
	}

	for (int i = 0; i < 10; ++i)
	{
		consumidores[i].join();
	}

	return 0;
}