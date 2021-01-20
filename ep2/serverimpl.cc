#include "serverimpl.h"

//
// IMPLEMENTACAO DA CLASSE ServerImpl
//
ServerImpl::ServerImpl(){}


//
//
//
void ServerImpl::f1() {
  for (int i = 0; i < 1000; i++);
}


//
//
//
CORBA::Long ServerImpl::f2( CORBA::Long a1 ) {

  CORBA::Long i;
  for (i = 0; i < a1; i++);
  return i;
}
  

//
//
//
CORBA::Long ServerImpl::f3( CORBA::Long a1, CORBA::Long a2, CORBA::Long a3, CORBA::Long a4, CORBA::Long a5 ) {

  CORBA::Long i, f;
  f = a1 + a2 + a3 + a4 + a5;
  f /= 5;
  for (i = 0; i < f; i++);
  return i;
}


//
//
//
Server::TC::ts1 ServerImpl::f4( const Server::TC::ts1& a1, const Server::TC::ts1& a2, const Server::TC::ts1& a3, const Server::TC::ts1& a4, const Server::TC::ts1& a5 ) {
 
  Server::TC::ts1 ret;

  ret.x = a1.x + a2.x + a3.x + a4.x + a5.x;
  ret.y = a1.y + a2.y + a3.y + a4.y + a5.y;
  ret.z = a1.z + a2.z + a3.z + a4.z + a5.z;

  return ret;
}


//
//
//
void ServerImpl::f7( const char* a1 ) {

  int i, f;
  f = strlen(a1);
  for (i = 0; i < 90*f; i++)
    strcmp(a1,a1);
}
