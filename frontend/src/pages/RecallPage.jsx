import { useState } from "react";
import { checkRecall } from "../api/recallApi";

export default function RecallPage() {
  const [lotNumber, setLotNumber] = useState("");
  const [result, setResult] = useState(null);

  const handleSearch = async () => {
    const data = await checkRecall(lotNumber);
    setResult(data);
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>의약품 리콜 조회</h2>

      <input
        value={lotNumber}
        onChange={(e) => setLotNumber(e.target.value)}
        placeholder="LOT 번호 입력"
      />

      <button onClick={handleSearch}>조회</button>

      {result && (
        <div style={{ marginTop: 20 }}>
          <h3>{result.status}</h3>
          <p>{result.message}</p>
          <p>{result.dangerLevel}</p>
          <p>{result.expirationDate}</p>
        </div>
      )}
    </div>
  );
}
