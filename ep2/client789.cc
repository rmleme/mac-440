#include <iostream>
#include <fstream>
#include <sys/time.h>

#include "serverimpl.h"

using namespace CORBA;
using namespace Server;

void test789 (TC_ptr server, char *arq);

int main(int argn, char **argv) {

  //
  // Cliente CORBA C++
  //
  //

  // Le a IOR para o objeto servidor do arquivo server.ref
  string srvstr;
  ifstream is("server.ref");
  is >> srvstr;

  // pega uma referencia para o servidor
  ORB_ptr orbref = ORB_init(argn, argv);

  // pega uma referencia para o objeto server no servidor
  Object_ptr objref = orbref->string_to_object(srvstr.c_str());

  // faz o narrow de objref para server
  TC_ptr server = TC::_narrow(objref);

  
  cout << "****** Client rodando ******\n";
  //agora e so usar o objeto serv

  test789 (server, argv[1]);


  //Libera memoria
  release(server);
  release(objref);
  release(orbref);
}
