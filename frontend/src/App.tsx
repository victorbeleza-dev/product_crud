import React, { useState, useEffect } from 'react';
import axios, { AxiosResponse } from 'axios';

interface Product {
  id: number;
  name: string;
  price: number;
}

function App() {
  const [products, setProducts] = useState<Product[]>([]);
  const [name, setName] = useState<string>('');
  const [price, setPrice] = useState<number>(0);
  const [editingProductId, setEditingProductId] = useState<number | null>(null);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const response: AxiosResponse<Product[]> = await axios.get('http://localhost:8080/products');
      setProducts(response.data);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const addProduct = async () => {
    try {
      if (editingProductId) {
        await axios.put(`http://localhost:8080/products/${editingProductId}`, {
          name,
          price,
        });
      } else {
        await axios.post('http://localhost:8080/products', { name, price });
      }
      fetchProducts();
      setName('');
      setPrice(0);
      setEditingProductId(null);
    } catch (error) {
      console.error('Error adding/editing product:', error);
    }
  };

  const editProduct = (id: number) => {
    const productToEdit = products.find((product) => product.id === id);
    if (productToEdit) {
      setName(productToEdit.name);
      setPrice(productToEdit.price);
      setEditingProductId(id);
    }
  };

  const deleteProduct = async (id: number) => {
    try {
      await axios.delete(`http://localhost:8080/products/${id}`);
      fetchProducts();
    } catch (error) {
      console.error('Error deleting product:', error);
    }
  };

  return (
    <div>
      <h1>Lista de Produtos</h1>
      <ul>
        {products.map((product) => (
          <li key={product.id}>
            <span>{product.name} - {product.price}</span>
            <button onClick={() => deleteProduct(product.id)}>Excluir</button>
            <button onClick={() => editProduct(product.id)}>Editar</button>
          </li>
        ))}
      </ul>
      <h2>Adicionar/Editar Produto</h2>
      <form>
        <input
          type="text"
          placeholder="Nome"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          type="number"
          placeholder="Preço"
          value={price}
          onChange={(e) => setPrice(parseInt(e.target.value))}
        />
        <button onClick={addProduct}>
          {editingProductId ? 'Editar' : 'Adicionar'}
        </button>
      </form>
    </div>
  );
}

export default App;
