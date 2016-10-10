#simulation d'une chaine (simulateu de nb random)
N=100000;
m=199017;
f=function(x,a=4,b=2,m=9){
  (a*x+b)%%m;
}
x={};
x[1]=1 ##SEED DE SIM (origine du calcule)
for(i in 2:N){
  x[i]=f(x[i-1],a=24298,b=99991,m);
}

plot(x[1:(N-1)],x[2:N]);
#hist(x,breaks=71);

suits={};
for(i in 1:m){
  suits[i]=x[i];
}


print("La chaine est :");
print(suits)