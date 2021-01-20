#include <iostream>
#include <fstream>
#include <pthread.h>
#include "server.h"
#include "serverimpl.h"

using namespace CORBA;
using namespace PortableServer;
using namespace Server;

ORB_ptr orb;

void test789 (TC_ptr server, char *arq);

void *OrbRoda (void * args) {
  orb->run();
  return NULL;
}

int main(int argn, char **argv) {
  
  //
  // Inicializa o servidor Corba c++
  //
  //

  // Inicializa o ORB
  orb = ORB_init(argn, argv);

  // Pega referencia para root POA
  Object_ptr obj = orb->resolve_initial_references("RootPOA");
  POA_ptr poa = POA::_narrow(obj);

  // Instancia o objeto servante
  ServerImpl server_impl;

  // Registra o servant no ORB
  TC_ptr server = server_impl._this();
  cout << server <<endl;

  // Salva a referencia como uma string no arquivo server.ref
  String_var objstr = orb->object_to_string(server);
  ofstream os("server.ref");
  if (!os) {cout << "Erro em criacao de arquivo" << endl; return 1;}
  os << (char*) objstr << endl;
  os.close();
  
  // Activa o POA manager
  poa->the_POAManager()->activate();
  
  cout << "*** Server rodando ***\n";
 
  // Espera por requisicoes em outra thread
  pthread_t id;  
  pthread_create(&id, NULL, OrbRoda, NULL);
  pthread_detach(id);


  //
  // Cliente CORBA C++
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
  TC_ptr serverRemoto = TC::_narrow(objref);

  
  cout << "****** Client rodando ******\n";
  //agora e so usar o objeto serv


  test789 (serverRemoto, argv[1]);


  //desliga o servidor
  orb->shutdown(0);
 
 //Libera memoria client
  release(server);
  release(objref);
  release(orbref);

  // Libera memoria Server
  release(server);
  release(poa);
  release(obj);
  release(orb);

  return 0;
}
