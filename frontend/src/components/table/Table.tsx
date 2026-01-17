import React, { useState } from 'react';
import './Table.css';

export interface Config {
    enableCreation: boolean;
    enableEdition: boolean;
    enableDeletion: boolean;
}

export interface Column<T> {
  header: string;
  accessor: keyof T;
  mandatoryForCreation?: boolean;
  editable?: boolean;
}

interface TableProps<T> {
  config: Config;
  columns: Column<T>[];
  data: T[];
  pageSize?: number;
  onEdit?: (row: T, rowIndex: number) => void;
  onDelete?: (row: T, rowIndex: number) => void;
  onCreate?: (row: T) => void;
}

function Table<T extends object>({ config, columns, data, pageSize = 5, onEdit, onDelete, onCreate }: TableProps<T>) {
  const [page, setPage] = useState(1);
  const [createData, setCreateData] = useState<Partial<T>>({});
  const [editRow, setEditRow] = useState<number | null>(null);
  const [editData, setEditData] = useState<Partial<T>>({});
  const [deleteRow, setDeleteRow] = useState<number | null>(null);
  const totalPages = Math.ceil(data.length / pageSize);
  const paginatedData = data.slice((page - 1) * pageSize, page * pageSize);

  const handleEditClick = (row: T, idx: number) => {
    setEditRow(idx);
    setEditData({ ...row });
  };

  const handleEditChange = (accessor: keyof T, value: any) => {
    setEditData(prev => ({ ...prev, [accessor]: value }));
  };

  const handleEditSubmit = (rowIndex: number) => {
    if (onEdit && editData) {
      onEdit(editData as T, rowIndex + (page - 1) * pageSize);
    }
    setEditRow(null);
    setEditData({});
  };

  const handleDeleteClick = (idx: number) => {
    setDeleteRow(idx);
  };

  const handleDeleteConfirm = (rowIndex: number) => {
    if (onDelete) {
      onDelete(paginatedData[rowIndex], rowIndex + (page - 1) * pageSize);
    }
    setDeleteRow(null);
  };

  const handleDeleteCancel = () => {
    setDeleteRow(null);
  };

  const handleCreateChange = (accessor: keyof T, value: any) => {
    setCreateData(prev => ({ ...prev, [accessor]: value }));
  };

  const isCreateDisabled = columns.some(col => col.mandatoryForCreation && (createData[col.accessor] === undefined || createData[col.accessor] === ''));
  const handleCreate = () => {
    if (onCreate && !isCreateDisabled) {
      onCreate(createData as T);
      setCreateData({});
    }
  };

  const handlePrev = () => {
    setPage((p) => Math.max(1, p - 1));
  };

  const handleNext = () => {
    setPage((p) => Math.min(totalPages, p + 1));
  };

  return (
    <div>
      <div className="custom-table-wrapper">
        <table className="table custom-table custom-table-striped custom-table-bordered">
          <thead>
            <tr>
              {columns.map((column) => (
                <th key={column.header}>{column.header}</th>
              ))}
              {config.enableEdition && <th>Edit</th>}
              {config.enableDeletion && <th>Delete</th>}
            </tr>
          </thead>
          <tbody>
            <tr>
              {columns.map((column) => (
                <td key={String(column.accessor)}>
                  {config.enableCreation && (column.editable === undefined || column.editable) ? (
                    <input
                      className="editable-table-input"
                      value={createData[column.accessor] !== undefined ? String(createData[column.accessor]) : ''}
                      onChange={e => handleCreateChange(column.accessor, e.target.value)}
                      placeholder={column.header + (column.mandatoryForCreation ? ' *' : '')}
                    />
                  ) : (
                    ''
                  )}
                </td>
              ))}
              {config.enableCreation && (
                <td colSpan={(config.enableEdition && config.enableDeletion) ? 2 : 1} className="editable-table-center-cell">
                  <button
                    className="btn btn-success btn-sm"
                    onClick={handleCreate}
                    disabled={isCreateDisabled}
                  >
                    Create
                  </button>
                </td>
              )}
            </tr>
            {paginatedData.map((row, rowIndex) => (
              <tr key={rowIndex + (page - 1) * pageSize}>
                {columns.map((column) => (
                  <td key={String(column.accessor)}>
                    {editRow === rowIndex && (column.editable === undefined || column.editable) ? (
                      <input
                        className="editable-table-input"
                        value={editData[column.accessor] !== undefined ? String(editData[column.accessor]) : ''}
                        onChange={e => handleEditChange(column.accessor, e.target.value)}
                      />
                    ) : (
                      String(row[column.accessor])
                    )}
                  </td>
                ))}
                {config.enableEdition && (
                  <td>
                    {editRow === rowIndex ? (
                      <button className="btn btn-success btn-sm" onClick={() => handleEditSubmit(rowIndex)}>
                        Submit
                      </button>
                    ) : (
                      <button className="btn btn-primary btn-sm" onClick={() => handleEditClick(row, rowIndex)}>
                        Edit
                      </button>
                    )}
                  </td>
                )}
                {config.enableDeletion && (
                  <td>
                    <button className="btn btn-danger btn-sm" onClick={() => handleDeleteClick(rowIndex)}>
                      Delete
                    </button>
                    {deleteRow === rowIndex && (
                      <div className="modal-backdrop">
                        <div className="modal-confirm">
                          <div>Are you sure you want to delete this row?</div>
                          <div className="editable-table-modal-actions">
                            <button className="btn btn-danger btn-sm" onClick={() => handleDeleteConfirm(rowIndex)}>
                              Submit
                            </button>
                            <button className="btn btn-secondary btn-sm editable-table-cancel-btn" onClick={handleDeleteCancel}>
                              Cancel
                            </button>
                          </div>
                        </div>
                      </div>
                    )}
                  </td>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {totalPages > 1 && (
        <nav className="d-flex justify-content-center align-items-center mt-3">
          <ul className="pagination mb-0">
            <li className={`page-item${page === 1 ? ' disabled' : ''}`}>
              <button className="page-link" onClick={handlePrev} disabled={page === 1}>&laquo;</button>
            </li>
            {[...Array(totalPages)].map((_, i) => (
              <li key={i} className={`page-item${page === i + 1 ? ' active' : ''}`}>
                <button className="page-link" onClick={() => setPage(i + 1)}>{i + 1}</button>
              </li>
            ))}
            <li className={`page-item${page === totalPages ? ' disabled' : ''}`}>
              <button className="page-link" onClick={handleNext} disabled={page === totalPages}>&raquo;</button>
            </li>
          </ul>
        </nav>
      )}
    </div>
  );
}

export default Table;
