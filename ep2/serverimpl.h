#ifndef serverimpl_h
#define serverimpl_h

#include "server.h"

//using namespace std;
//using namespace CORBA;
//using namespace Server;


class ServerImpl : public POA_Server::TC {

 public:

  ServerImpl();

  void f1();
  
  CORBA::Long f2( CORBA::Long a1 );
  
  CORBA::Long f3( CORBA::Long a1, CORBA::Long a2, CORBA::Long a3, CORBA::Long a4, CORBA::Long a5 );
  
  Server::TC::ts1 f4( const Server::TC::ts1& a1, const Server::TC::ts1& a2, const Server::TC::ts1& a3, const Server::TC::ts1& a4, const Server::TC::ts1& a5 );
  
  void f7( const char* a1 );


};

#endif
