package com.example.admin.message_search;

class KMP {
    int prefix[];
    char input[],pattern[];
    KMP(){

    }

    void prefixTable(){
        prefix[0]=0;
        int k=0;
        for(int i=1;i<pattern.length;i++){
            while((k>0)&&(pattern[k]!=pattern[i]))
                k=prefix[k-1];
            if(pattern[k]==pattern[i])
                k++;
            prefix[i]=k;
        }
    }

    int search(String input,String pattern){
        prefix=new int[pattern.length()];
        this.input=new char[input.length()];
        this.pattern=new char[pattern.length()];
        this.input=input.toCharArray();
        this.pattern=pattern.toCharArray();
        int i=0,j=0;
        this.prefixTable();
        while(i<this.input.length && j<this.pattern.length){
            if(this.input[i]==this.pattern[j]){
                i++;j++;
            }
            else{
                if(j==0)
                    i++;
                else
                    j=prefix[j-1];
            }
        }
        if(j==this.pattern.length)
           return 1;
        else
            return 0;
    }
}
