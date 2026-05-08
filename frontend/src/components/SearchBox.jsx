export default function SearchBox({ lotNumber, setLotNumber, onSearch }) {
  const handleKeyDown = (e) => {
    if (e.key === "Enter") onSearch();
  };

  return (
    <div style={{ marginBottom: 20 }}>
      <input
        value={lotNumber}
        onChange={(e) => setLotNumber(e.target.value)}
        onKeyDown={handleKeyDown}
        placeholder="LOT 번호 입력"
        style={{ padding: 10, width: 200 }}
      />
      <button onClick={onSearch} style={{ marginLeft: 10 }}>
        조회
      </button>
    </div>
  );
}
