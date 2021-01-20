#include <iostream>
#include <fstream>

#include "server.h"
#include "serverimpl.h"
#define instancias  2048
using namespace CORBA;
using namespace PortableServer;
using namespace Server;

int main(int argn, char **argv) {

  int i;
  ServerImpl **mem;
  TC_ptr *ptrs;
  mem = (ServerImpl **)malloc (instancias * sizeof (ServerImpl *));
  ptrs = (TC_ptr *)malloc (instancias * sizeof (TC_ptr));
  // Inicializa o ORB
  ORB_ptr orb = ORB_init(argn, argv);

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

  for (i = 0; i < instancias; i++) {
    //INSTANCIAR OBJETOS CORBA    
    mem[i] = new ServerImpl;
    ptrs[i] = mem[i]->_this();
    
    //ServerImpl server_impl;
    
  }
 
  // Espera por requisicoes
  orb->run();

  orb->shutdown(0);
 
  // Libera memoria
  release(server);
  release(poa);
  release(obj);
  release(orb);

  return 0;
}
