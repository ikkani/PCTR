#include <thread>
#include <iostream>
#include <vector>
#include <mutex>

using namespace std;

struct S
{
	mutex cerrojo;
	recursive_mutex cerrojoRec;
	void A(){
		//cerrojo.lock();
		//cerrojoRec.lock();
		lock_guard<std::recursive_mutex> gl(cerrojoRec);
		cout << "A" << endl;
		//cerrojo.unlock();
		//cerrojoRec.unlock();
	}
	
	void B(){
		//cerrojoRec.lock();
		lock_guard<std::recursive_mutex> gl(cerrojoRec);
		//cerrojo.lock();
		cout << "B" << endl;
		//cerrojoRec.unlock();
		
		//cerrojo.unlock();
	}
	void C(){
		//cerrojoRec.lock();
		lock_guard<std::recursive_mutex> gl(cerrojoRec);
		//cerrojo.lock();
		A();
		B();
		//cerrojoRec.unlock();
		
		//cerrojo.unlock();
	}
	
};

int main(int argc, char const *argv[])
{

	thread hilo([](){
		S s;
		s.C();
	});
	hilo.join();	

	return 0;
}