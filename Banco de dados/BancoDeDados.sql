PGDMP  5                    |            fipp    15.7    16.3 .    ,           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            -           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            .           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            /           1262    16396    fipp    DATABASE     {   CREATE DATABASE fipp WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE fipp;
                postgres    false            �            1259    16437 	   categoria    TABLE     c   CREATE TABLE public.categoria (
    cat_id integer NOT NULL,
    cat_nome character varying(20)
);
    DROP TABLE public.categoria;
       public         heap    postgres    false            �            1259    16497    categoria_cat_id_seq    SEQUENCE     �   CREATE SEQUENCE public.categoria_cat_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.categoria_cat_id_seq;
       public          postgres    false    219            0           0    0    categoria_cat_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.categoria_cat_id_seq OWNED BY public.categoria.cat_id;
          public          postgres    false    222            �            1259    16405    comanda    TABLE     �   CREATE TABLE public.comanda (
    com_id integer NOT NULL,
    com_numero integer,
    com_nome character varying(40),
    com_data timestamp with time zone,
    gar_id integer
);
    DROP TABLE public.comanda;
       public         heap    postgres    false            �            1259    16481    comanda_com_id_seq    SEQUENCE     �   CREATE SEQUENCE public.comanda_com_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.comanda_com_id_seq;
       public          postgres    false    216            1           0    0    comanda_com_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.comanda_com_id_seq OWNED BY public.comanda.com_id;
          public          postgres    false    221            �            1259    16398    garcon    TABLE     3  CREATE TABLE public.garcon (
    gar_id integer NOT NULL,
    gar_nome character varying(40),
    gar_cpf character varying(14),
    gar_cep character varying(9),
    gar_end character varying(50),
    gar_cidade character varying(50),
    gar_uf character varying(2),
    gar_fone character varying(15)
);
    DROP TABLE public.garcon;
       public         heap    postgres    false            �            1259    16397    garcon_gar_id_seq    SEQUENCE     �   CREATE SEQUENCE public.garcon_gar_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.garcon_gar_id_seq;
       public          postgres    false    215            2           0    0    garcon_gar_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.garcon_gar_id_seq OWNED BY public.garcon.gar_id;
          public          postgres    false    214            �            1259    16416    item    TABLE     \   CREATE TABLE public.item (
    it_quant integer,
    com_id integer,
    prod_id integer
);
    DROP TABLE public.item;
       public         heap    postgres    false            �            1259    16425    produto    TABLE     �   CREATE TABLE public.produto (
    pro_id integer NOT NULL,
    pro_nome character varying(50),
    pro_preco character varying(50),
    uni_id integer,
    cat_id integer
);
    DROP TABLE public.produto;
       public         heap    postgres    false            �            1259    16519    produto_pro_id_seq    SEQUENCE     �   CREATE SEQUENCE public.produto_pro_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.produto_pro_id_seq;
       public          postgres    false    218            3           0    0    produto_pro_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.produto_pro_id_seq OWNED BY public.produto.pro_id;
          public          postgres    false    224            �            1259    16444    unidade    TABLE     a   CREATE TABLE public.unidade (
    uni_id integer NOT NULL,
    uni_nome character varying(20)
);
    DROP TABLE public.unidade;
       public         heap    postgres    false            �            1259    16505    unidade_uni_id_seq    SEQUENCE     �   CREATE SEQUENCE public.unidade_uni_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.unidade_uni_id_seq;
       public          postgres    false    220            4           0    0    unidade_uni_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.unidade_uni_id_seq OWNED BY public.unidade.uni_id;
          public          postgres    false    223            �           2604    16498    categoria cat_id    DEFAULT     t   ALTER TABLE ONLY public.categoria ALTER COLUMN cat_id SET DEFAULT nextval('public.categoria_cat_id_seq'::regclass);
 ?   ALTER TABLE public.categoria ALTER COLUMN cat_id DROP DEFAULT;
       public          postgres    false    222    219            ~           2604    16482    comanda com_id    DEFAULT     p   ALTER TABLE ONLY public.comanda ALTER COLUMN com_id SET DEFAULT nextval('public.comanda_com_id_seq'::regclass);
 =   ALTER TABLE public.comanda ALTER COLUMN com_id DROP DEFAULT;
       public          postgres    false    221    216            }           2604    16401    garcon gar_id    DEFAULT     n   ALTER TABLE ONLY public.garcon ALTER COLUMN gar_id SET DEFAULT nextval('public.garcon_gar_id_seq'::regclass);
 <   ALTER TABLE public.garcon ALTER COLUMN gar_id DROP DEFAULT;
       public          postgres    false    215    214    215                       2604    16520    produto pro_id    DEFAULT     p   ALTER TABLE ONLY public.produto ALTER COLUMN pro_id SET DEFAULT nextval('public.produto_pro_id_seq'::regclass);
 =   ALTER TABLE public.produto ALTER COLUMN pro_id DROP DEFAULT;
       public          postgres    false    224    218            �           2604    16506    unidade uni_id    DEFAULT     p   ALTER TABLE ONLY public.unidade ALTER COLUMN uni_id SET DEFAULT nextval('public.unidade_uni_id_seq'::regclass);
 =   ALTER TABLE public.unidade ALTER COLUMN uni_id DROP DEFAULT;
       public          postgres    false    223    220            $          0    16437 	   categoria 
   TABLE DATA           5   COPY public.categoria (cat_id, cat_nome) FROM stdin;
    public          postgres    false    219   B2       !          0    16405    comanda 
   TABLE DATA           Q   COPY public.comanda (com_id, com_numero, com_nome, com_data, gar_id) FROM stdin;
    public          postgres    false    216   �2                  0    16398    garcon 
   TABLE DATA           k   COPY public.garcon (gar_id, gar_nome, gar_cpf, gar_cep, gar_end, gar_cidade, gar_uf, gar_fone) FROM stdin;
    public          postgres    false    215   S3       "          0    16416    item 
   TABLE DATA           9   COPY public.item (it_quant, com_id, prod_id) FROM stdin;
    public          postgres    false    217   V5       #          0    16425    produto 
   TABLE DATA           N   COPY public.produto (pro_id, pro_nome, pro_preco, uni_id, cat_id) FROM stdin;
    public          postgres    false    218   s5       %          0    16444    unidade 
   TABLE DATA           3   COPY public.unidade (uni_id, uni_nome) FROM stdin;
    public          postgres    false    220   �5       5           0    0    categoria_cat_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.categoria_cat_id_seq', 7, true);
          public          postgres    false    222            6           0    0    comanda_com_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.comanda_com_id_seq', 14, true);
          public          postgres    false    221            7           0    0    garcon_gar_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.garcon_gar_id_seq', 25, true);
          public          postgres    false    214            8           0    0    produto_pro_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.produto_pro_id_seq', 8, true);
          public          postgres    false    224            9           0    0    unidade_uni_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.unidade_uni_id_seq', 6, true);
          public          postgres    false    223            �           2606    16503    categoria categoria_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (cat_id);
 B   ALTER TABLE ONLY public.categoria DROP CONSTRAINT categoria_pkey;
       public            postgres    false    219            �           2606    16487    comanda comanda_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.comanda
    ADD CONSTRAINT comanda_pkey PRIMARY KEY (com_id);
 >   ALTER TABLE ONLY public.comanda DROP CONSTRAINT comanda_pkey;
       public            postgres    false    216            �           2606    16403    garcon garcon_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.garcon
    ADD CONSTRAINT garcon_pkey PRIMARY KEY (gar_id);
 <   ALTER TABLE ONLY public.garcon DROP CONSTRAINT garcon_pkey;
       public            postgres    false    215            �           2606    16525    produto produto_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (pro_id);
 >   ALTER TABLE ONLY public.produto DROP CONSTRAINT produto_pkey;
       public            postgres    false    218            �           2606    16511    unidade unidade_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.unidade
    ADD CONSTRAINT unidade_pkey PRIMARY KEY (uni_id);
 >   ALTER TABLE ONLY public.unidade DROP CONSTRAINT unidade_pkey;
       public            postgres    false    220            �           2606    16526    comanda Comanda_Garcon    FK CONSTRAINT     �   ALTER TABLE ONLY public.comanda
    ADD CONSTRAINT "Comanda_Garcon" FOREIGN KEY (gar_id) REFERENCES public.garcon(gar_id) NOT VALID;
 B   ALTER TABLE ONLY public.comanda DROP CONSTRAINT "Comanda_Garcon";
       public          postgres    false    216    3203    215            �           2606    16531    item Item_Comanda    FK CONSTRAINT     �   ALTER TABLE ONLY public.item
    ADD CONSTRAINT "Item_Comanda" FOREIGN KEY (com_id) REFERENCES public.comanda(com_id) NOT VALID;
 =   ALTER TABLE ONLY public.item DROP CONSTRAINT "Item_Comanda";
       public          postgres    false    217    3205    216            �           2606    16536    item Item_Produto    FK CONSTRAINT     �   ALTER TABLE ONLY public.item
    ADD CONSTRAINT "Item_Produto" FOREIGN KEY (prod_id) REFERENCES public.produto(pro_id) NOT VALID;
 =   ALTER TABLE ONLY public.item DROP CONSTRAINT "Item_Produto";
       public          postgres    false    3207    217    218            �           2606    16546    produto Produto_Categoria    FK CONSTRAINT     �   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT "Produto_Categoria" FOREIGN KEY (cat_id) REFERENCES public.categoria(cat_id) NOT VALID;
 E   ALTER TABLE ONLY public.produto DROP CONSTRAINT "Produto_Categoria";
       public          postgres    false    218    219    3209            �           2606    16541    produto Produto_Unidade    FK CONSTRAINT     �   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT "Produto_Unidade" FOREIGN KEY (uni_id) REFERENCES public.unidade(uni_id) NOT VALID;
 C   ALTER TABLE ONLY public.produto DROP CONSTRAINT "Produto_Unidade";
       public          postgres    false    220    218    3211            $   P   x�3�t�ON-�2�JM+�LO-J�+I�2�N�IOL�����2�t�(-*J,N��2�t���M�+�/Vp+J
s��qqq a�      !   �   x�m�;
�@����*����L�i�Y���͠)"���w"�Nw>~�MNY=�'��#Q�ڣ�a���/j��Ώ���f��гT�q���j �wͪ�R�J"G!@����C�u&���8�]w�oeQ���@K�T����}�uD.�4�;��sOJ�9          �  x���ώ�0�ϓ��c9$��	��U[UE�%]�j{�L�9�J�k��b'�$��Q������+���!@�4O�Y
��Ʒ�֍�{�4��yx��y	o�a�w�H�t� �d&(�y y�5Ջ���mLJ��b<��LF��BP��qN�![��Zm�o��p��h7�v�r�������%Joj�K�v���JM�1uc(c��8PON����TOŅm��_#���G"��J���ix�k�d<�j��ͯ�[Wٺ�G�d��@7����tHI�_����_g�dR	)Jx�i�_)�u�p����R���d���v/w�Q����åƕ��ƅ{6��~���c�eq�a߹�ƛ�=d��ݓg����O�B�X�n��|#��K�݊�br "�v]�&Èwmz�C�Q�8L� Q�a5�T
T~�DKК���Rk��Yc�7����f�F�i�̒a�j�5��?n�wvu������zL�(�=d�@      "      x������ � �      #   e   x�U�1
� ���� �Y�RSGh�z�*���jj��6�N�x:4�2
���S�����cI|�pD���.�֠���Sva�P�����_�J�����n�!�      %   F   x�3�tO,*JLKTH-�2�HL�/I�2�tN̬H�2�t-.H-�WHIUp�(�,N��2��s��qqq ��     